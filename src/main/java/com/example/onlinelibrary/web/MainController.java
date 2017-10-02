package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.*;
import com.example.onlinelibrary.persistence.UserDao;
import com.example.onlinelibrary.services.BookService;
import com.example.onlinelibrary.services.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
@Scope("session")
public class MainController {
    @Autowired
    private UserDao userDao;
    private final BookService bookService;
    private String lastSearchQuery = "";
    private int booksOnPage = 20;
    private int currentPage = 0;
    private int numOfBooks = 0;
    private List<Book> lastSearchResult;
    private User user;

    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "home"})
    public String getHome(Model model) {
        updateUser();
        model.addAttribute("username", "");
        model.addAttribute("search", lastSearchQuery);
        model.addAttribute("booksOnPage", booksOnPage);
        model.addAttribute("numOfBooks", numOfBooks);
        if (user != null) model.addAttribute("username", user.getUsername());
        if (!lastSearchQuery.equals("")) {
            model.addAttribute("searchResult", bookService.findByTitle(Query.builder()
                    .setTitle(lastSearchQuery)
                    .setMaxResult(booksOnPage)
                    .setStartIndex(booksOnPage * currentPage)
                    .build()));
        } else {
            model.addAttribute("searchResult", new ArrayList<Book>());
        }
        model.addAttribute("currentPage", currentPage);
        return "home";
    }

    @GetMapping("/user")
    public String getHello(Model model) {
        updateUser();
        model.addAttribute("username", "");
        model.addAttribute("roles", "");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (user != null) {
            List<Book> favoriteBooks = user.getFavoriteBooks()
                    .stream().map(x -> bookService.findByTitle(
                            Query.builder()
                                    .setTitle(x.getGoogleId())
                                    .setStartIndex(0)
                                    .setMaxResult(1)
                                    .build()).get(0)).collect(Collectors.toList());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getAuthorities().stream().map(Role::getAuthority).collect(joining(",")));
            model.addAttribute("favorites", favoriteBooks);
        }
        return "user";
    }

    @GetMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        if (logout != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication = null;
            //model.addAttribute("username", "");
            lastSearchQuery = "";
            updateUser();
        }
        return "login";
    }

    @GetMapping("/content")
    public String getContent(
            @RequestParam(value = "query", required = true) String query,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "onPage", required = false) String onPage,
            @RequestParam(value = "lang", required = false) String language,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "print", required = false) String print,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "downloadable", required = false) Boolean downloadable,
            Model model) {
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }
        if (onPage != null && !onPage.equals("undefined")) {
            booksOnPage = Integer.parseInt(onPage);
        }

        List<Book> result = bookService.findByTitle(Query.builder()
                .setTitle(query)
                .setMaxResult(booksOnPage)
                .setFilter(filter)
                .setPrintType(print)
                .setDownloadable(downloadable)
                .setLanguage(language)
                .setOrder(order)
                .setStartIndex(currentPage * booksOnPage)
                .build());
        lastSearchResult = result;

        lastSearchQuery = query;
        if (result != null) {
            model.addAttribute("searchResult", result);
            log.info("user request: " + query + "; page: " + page);
            model.addAttribute("booksOnPage", booksOnPage);
            model.addAttribute("numOfBooks", numOfBooks);
            model.addAttribute("currentPage", currentPage);
        }
        updateUser();
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            //List<Book> favBookList = user.getFavoriteBooks().stream().map(x -> bookService.findByGoogleId(Query.builder().setTitle(x.getGoogleId()).build())).collect(Collectors.toList());
            //model.addAttribute("favoriteBooks", favBookList);
            //TODO add fav list
        }
        if (result == null) return "not_found";
        return "content";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable String id, Model model) {
        Book book = bookService.findByGoogleId(
                Query.builder()
                        .setTitle(id)
                        .setStartIndex(0)
                        .setMaxResult(1)
                        .build());
        model.addAttribute("book", book);
        return "book";
    }

    @PostMapping("/add_favorite")
    public String addBookToFavorite(
            @RequestParam(value = "googleBookId", required = false) String googleBookId) {
        updateUser();
        if (user != null) {
            Set<Books> favoriteBooks = userDao.findByUserName(user.getUsername()).orElse(new User()).getFavoriteBooks();
            Books newFavBook = Books.builder().googleId(googleBookId).build();
            if (!favoriteBooks.contains(newFavBook)) {
                favoriteBooks.add(newFavBook);
                user.setFavoriteBooks(favoriteBooks);
                userDao.save(user);
            }
        }
        log.info(googleBookId);
        return "alert";
    }

    private void updateUser() {
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
                try {
                    user = (User) authentication.getPrincipal();
                } catch (ClassCastException ex) {
                    log.error(ex);
                }
            } else {
                user = null;
            }
        }
    }
}
