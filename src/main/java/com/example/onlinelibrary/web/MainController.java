package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.domain.Role;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.services.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
@Scope("session")
public class MainController {
    private final BookService bookService;
    private String lastSearchQuery = "";
    private int booksOnPage = 20;
    private int currentPage = 0;
    private int numOfBooks = 0;
    private List<Book> lastSearchResult = null;

    @Autowired
    public MainController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/", "home"})
    public String getHome(Model model) {
        model.addAttribute("username", "");
        model.addAttribute("search", lastSearchQuery);
        model.addAttribute("booksOnPage", booksOnPage);
        model.addAttribute("numOfBooks", numOfBooks);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            try {
                User user = (User) authentication.getPrincipal();
                model.addAttribute("username", user.getUsername());
            } catch (ClassCastException ex) {
                log.info(ex);
            }
        }
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
        model.addAttribute("username", "");
        model.addAttribute("roles", "");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = (User) authentication.getPrincipal();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getAuthorities().stream().map(Role::getAuthority).collect(joining(",")));
        } catch (ClassCastException ex) {
            log.info(ex);
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
            model.addAttribute("username", "");
            lastSearchQuery = "";
        }
        return "login";
    }

    @GetMapping("/content")
    public String getContent(
            @RequestParam(value = "query", required = true) String query,
            @RequestParam(value = "page", required = false) String page,
            Model model) {
        if (page != null) {
            currentPage = Integer.parseInt(page);
        }

        //TODO: add another req params
        List<Book> result = bookService.findByTitle(Query.builder()
                .setTitle(query)
                .setMaxResult(booksOnPage)
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
        return "content";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable String id, Model model) {
        if (lastSearchResult != null) {
            Book book = lastSearchResult.stream().filter(b -> b.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            model.addAttribute("book", book);
        }
        return "book";
    }
}
