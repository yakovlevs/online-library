package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.*;
import com.example.onlinelibrary.persistence.UserDao;
import com.example.onlinelibrary.services.BookService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @Value("${application.robokassa.merchantlogin}")
    private String merchantLogin;
    @Value("${application.robokassa.merchantpass1}")
    private String merchantPass1;

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
            List<Book> result = bookService.findByTitle(Query.builder()
                    .setTitle(lastSearchQuery)
                    .setMaxResult(booksOnPage)
                    .setStartIndex(booksOnPage * currentPage)
                    .build());
            model.addAttribute("searchResult", updateBookStatus(result, user));
        } else {
            model.addAttribute("searchResult", new ArrayList<Book>());
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("MerchantLogin", merchantLogin);
        //Book book = new Book().getSignatureValue()
        return "home";
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

        updateUser();
        if (result != null) {
            log.info("user request: " + query + "; page: " + page);
            model.addAttribute("booksOnPage", booksOnPage);
            model.addAttribute("numOfBooks", numOfBooks);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("searchResult", updateBookStatus(result, user));
            model.addAttribute("MerchantLogin", merchantLogin);
        }

        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        if (result == null) return "not_found";
        return "content";
    }


    @GetMapping("book/{id}")
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

    @GetMapping("/user")
    public String getHello(Model model) {
        updateUser();
        model.addAttribute("username", "");
        model.addAttribute("roles", "");
        model.addAttribute("MerchantLogin", merchantLogin);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getAuthorities().stream().map(Role::getAuthority).collect(joining(",")));
            getFavoritesBook(model);
            getPurchasedBooks(model);
        }
        return "user";
    }

    @PostMapping("/add_favorite")
    public String addBookToFavorite(
            @RequestParam(value = "googleBookId", required = false) String googleBookId) {
        updateUser();
        if (user != null) {
            Set<FavBook> favoriteBooks = userDao.findByUserName(user.getUsername()).orElse(new User()).getFavoriteBooks();
            FavBook newFavBook = FavBook.builder().googleId(googleBookId).build();
            if (!favoriteBooks.contains(newFavBook)) {
                favoriteBooks.add(newFavBook);
                user.setFavoriteBooks(favoriteBooks);
                userDao.save(user);
            }
        }
        log.info("book with id " + googleBookId + " added to fav.");
        return "alert";
    }

    @PostMapping("/remove_favorite")
    public String removeBookFromFavorite(
            @RequestParam(value = "googleBookId", required = false) String googleBookId) {
        updateUser();
        if (user != null) {
            Set<FavBook> favoriteBooks = userDao.findByUserName(user.getUsername()).orElse(new User()).getFavoriteBooks();
            FavBook newFavBook = FavBook.builder().googleId(googleBookId).build();
            if (favoriteBooks.contains(newFavBook)) {
                favoriteBooks.remove(newFavBook);
                user.setFavoriteBooks(favoriteBooks);
                userDao.save(user);
            }
        }
        log.info("book with id " + googleBookId + " removed from fav.");
        return "alert";
    }

    @GetMapping("/payment/result")
    @ResponseBody
    public String purchaseResult(
            @RequestParam(value = "OutSum", required = false) String outSum,
            @RequestParam(value = "InvId", required = false) String invId,
            @RequestParam(value = "Shp_book", required = false) String gbookId,
            @RequestParam(value = "Shp_user", required = false) String user,
            @RequestParam(value = "SignatureValue", required = false) String signatureValue
    ) {
        //Shp_book=${book.getId()}&Shp_user=${username}"
        log.info("Payment result: " + outSum + " " + invId + " " + signatureValue + " " + user + " " + gbookId);
        return "OK" + invId;
    }

    @GetMapping("payment/success")
    public String paymentSuccess() {
        return "/";
    }

    @GetMapping("payment/fail")
    public String paymentFail() {
        return "error";
    }

    @GetMapping("/user/favorites")
    public String getFavoritesBook(Model model) {
        updateUser();
        if (user != null) {
            List<Book> favoriteBooks = user.getFavoriteBooks()
                    .stream().map(x -> bookService.findByGoogleId(
                            Query.builder()
                                    .setTitle(x.getGoogleId())
                                    .setStartIndex(0)
                                    .setMaxResult(1)
                                    .build())).collect(Collectors.toList());
            model.addAttribute("favorites", updateBookStatus(favoriteBooks, user));
            model.addAttribute("username", user.getUsername());
        }
        return "user_fav";
    }

    @GetMapping("/user/purchased")
    public String getPurchasedBooks(Model model) {
        updateUser();
        if (user != null) {
            List<Book> purchasedBooks = user.getPurchasedBooks()
                    .stream().map(x -> bookService.findByGoogleId(
                            Query.builder()
                                    .setTitle(x.getGoogleId())
                                    .setStartIndex(0)
                                    .setMaxResult(1)
                                    .build())).collect(Collectors.toList());
            model.addAttribute("purchased", updateBookStatus(purchasedBooks, user));
            model.addAttribute("username", user.getUsername());
        }
        return "user_purchased";
    }

    private void updateUser() {
        if (user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
                try {
                    user = (User) authentication.getPrincipal();
                    user = userDao.findByUserName(user.getUsername()).orElse(null);
                } catch (ClassCastException ex) {
                    log.error(ex);
                }
            } else {
                user = null;
            }
        }
    }

    private List<Book> updateBookStatus(List<Book> bookList, User user) {
        if ((user != null) && (bookList != null)) {
            bookList = bookList.stream().peek(b -> {
                b.setFavorite(
                        user.getFavoriteBooks().stream().anyMatch(f -> f.getGoogleId().equals(b.getId())));
                b.setPurchased(
                        user.getPurchasedBooks().stream().anyMatch(f -> f.getGoogleId().equals(b.getId())));
                b.setSignatureValue(generateSignature(b.getPrice(), user.getUsername(), b.getId()));
            })
                    .collect(Collectors.toList());
        }
        return bookList;
    }

    private String generateSignature(Double price, String user, String id) {
        String result = merchantLogin + ":" + price + "::" + merchantPass1 + ":Shp_book=" + id + ":Shp_user=" + user;
        return stringToMd5(result);
    }

    private String stringToMd5(@NonNull String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(str.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
