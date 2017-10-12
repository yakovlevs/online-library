package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.*;
import com.example.onlinelibrary.payment.Robokassa;
import com.example.onlinelibrary.persistence.UserDao;
import com.example.onlinelibrary.services.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
@Scope("session")
public class MainController {
    private final UserDao userDao;
    private final Robokassa robokassa;
    private final BookService bookService;
    private final UserSession us;

    @Autowired
    public MainController(BookService bookService, UserDao userDao, Robokassa robokassa, UserSession us) {
        this.bookService = bookService;
        this.userDao = userDao;
        this.robokassa = robokassa;
        this.us = us;
    }

    @GetMapping({"/", "home"})
    public String getHome(Model model) {
        us.updateUser(userDao);
        model.addAttribute("username", "");
        model.addAttribute("search", us.getLastSearchQuery());
        model.addAttribute("booksOnPage", us.getBooksOnPage());
        model.addAttribute("numOfBooks", us.getNumOfBooks());
        if (us.getUser() != null) model.addAttribute("username", us.getUser().getUsername());
        if (!us.getLastSearchQuery().equals("")) {
            List<Book> result = bookService.findByTitle(Query.builder()
                    .setTitle(us.getLastSearchQuery())
                    .setMaxResult(us.getBooksOnPage())
                    .setStartIndex(us.getBooksOnPage() * us.getCurrentPage())
                    .build());
            model.addAttribute("searchResult", updateBookStatus(result, us.getUser()));
        } else {
            model.addAttribute("searchResult", new ArrayList<Book>());
        }
        model.addAttribute("currentPage", us.getCurrentPage());
        model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
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
            us.setLastSearchQuery("");
            us.updateUser(userDao);
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
            us.setCurrentPage(Integer.parseInt(page));
        }
        if (onPage != null && !onPage.equals("undefined")) {
            us.setBooksOnPage(Integer.parseInt(onPage));
        }
        List<Book> result = bookService.findByTitle(Query.builder()
                .setTitle(query)
                .setMaxResult(us.getBooksOnPage())
                .setFilter(filter)
                .setPrintType(print)
                .setDownloadable(downloadable)
                .setLanguage(language)
                .setOrder(order)
                .setStartIndex(us.getCurrentPage() * us.getBooksOnPage())
                .build());

        us.setLastSearchResult(result);
        us.setLastSearchQuery(query);
        us.updateUser(userDao);

        if (result != null) {
            log.info("user request: " + query + "; page: " + page);
            model.addAttribute("booksOnPage", us.getBooksOnPage());
            model.addAttribute("numOfBooks", us.getNumOfBooks());
            model.addAttribute("currentPage", us.getCurrentPage());
            model.addAttribute("searchResult", updateBookStatus(result, us.getUser()));
            model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        }

        if (us.getUser() != null) {
            model.addAttribute("username", us.getUser().getUsername());
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
        us.updateUser(userDao);
        model.addAttribute("username", "");
        model.addAttribute("roles", "");
        model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        if (us.getUser() != null) {
            model.addAttribute("username", us.getUser().getUsername());
            model.addAttribute("roles", us.getUser().getAuthorities().stream().map(Role::getAuthority).collect(joining(",")));
            getFavoritesBook(model);
            getPurchasedBooks(model);
        }
        return "user";
    }

    @PostMapping("/add_favorite")
    public String addBookToFavorite(
            @RequestParam(value = "googleBookId", required = false) String googleBookId) {
        us.updateUser(userDao);
        if (us.getUser() != null) {
            Set<FavBook> favoriteBooks = userDao.findByUserName(us.getUser().getUsername()).orElse(new User()).getFavoriteBooks();
            FavBook newFavBook = FavBook.builder().googleId(googleBookId).build();
            if (!favoriteBooks.contains(newFavBook)) {
                favoriteBooks.add(newFavBook);
                us.getUser().setFavoriteBooks(favoriteBooks);
                userDao.save(us.getUser());
            }
        }
        log.info("book with id " + googleBookId + " added to fav.");
        return "alert";
    }

    @PostMapping("/remove_favorite")
    public String removeBookFromFavorite(
            @RequestParam(value = "googleBookId", required = false) String googleBookId) {
        us.updateUser(userDao);
        if (us.getUser() != null) {
            Set<FavBook> favoriteBooks = userDao.findByUserName(us.getUser().getUsername()).orElse(new User()).getFavoriteBooks();
            FavBook newFavBook = FavBook.builder().googleId(googleBookId).build();
            if (favoriteBooks.contains(newFavBook)) {
                favoriteBooks.remove(newFavBook);
                us.getUser().setFavoriteBooks(favoriteBooks);
                userDao.save(us.getUser());
            }
        }
        log.info("book with id " + googleBookId + " removed from fav.");
        return "alert";
    }

    @PostMapping("/payment/result")
    @ResponseBody
    public String purchaseResult(
            @RequestParam(value = "OutSum", required = false) String price,
            @RequestParam(value = "InvId", required = false) String invId,
            @RequestParam(value = "Shp_book", required = false) String gbookId,
            @RequestParam(value = "Shp_user", required = false) String payer,
            @RequestParam(value = "SignatureValue", required = false) String signatureValue
    ) {
        boolean verification = robokassa.verifySignature(Double.parseDouble(price), invId, payer, gbookId, signatureValue);
        if (verification) {
            User user = userDao.findByUserName(payer).orElse(null);
            if (user != null) {
                Set<PurchasedBook> purchasedBooks = user.getPurchasedBooks();
                purchasedBooks.add(PurchasedBook.builder().googleId(gbookId).build());
                user.setPurchasedBooks(purchasedBooks);
                userDao.save(user);
                List<User> all = userDao.findAll();
                log.info(all);
                us.setUser(null);
            }
        }
        log.info("Payment result: " + price + " " + invId + " " + signatureValue + " " + payer + " " + gbookId);
        log.info("Signature verification: " + verification);
        return "OK" + invId;
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "/";
    }

    @GetMapping("/payment/fail")
    public String paymentFail() {
        return "error";
    }

    @GetMapping("/user/favorites")
    public String getFavoritesBook(Model model) {
        us.updateUser(userDao);
        if (us.getUser() != null) {
            List<Book> favoriteBooks = us.getUser().getFavoriteBooks()
                    .stream().map(x -> bookService.findByGoogleId(
                            Query.builder()
                                    .setTitle(x.getGoogleId())
                                    .setStartIndex(0)
                                    .setMaxResult(1)
                                    .build())).collect(Collectors.toList());
            model.addAttribute("favorites", updateBookStatus(favoriteBooks, us.getUser()));
            model.addAttribute("username", us.getUser().getUsername());
            model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        }
        return "user_fav";
    }

    @GetMapping("/user/purchased")
    public String getPurchasedBooks(Model model) {
        us.updateUser(userDao);
        if (us.getUser() != null) {
            List<Book> purchasedBooks = us.getUser().getPurchasedBooks()
                    .stream().map(x -> bookService.findByGoogleId(
                            Query.builder()
                                    .setTitle(x.getGoogleId())
                                    .setStartIndex(0)
                                    .setMaxResult(1)
                                    .build())).collect(Collectors.toList());
            model.addAttribute("purchased", updateBookStatus(purchasedBooks, us.getUser()));
            model.addAttribute("username", us.getUser().getUsername());
            model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        }
        return "user_purchased";
    }

    private List<Book> updateBookStatus(List<Book> bookList, User user) {
        if ((user != null) && (bookList != null)) {
            bookList = bookList.stream().peek(b -> {
                b.setFavorite(
                        user.getFavoriteBooks().stream().anyMatch(f -> f.getGoogleId().equals(b.getId())));
                b.setPurchased(
                        user.getPurchasedBooks().stream().anyMatch(f -> f.getGoogleId().equals(b.getId())));
                b.setSignatureValue(robokassa.generateSignature(b.getPrice(), user.getUsername(), b.getId()));
            })
                    .collect(Collectors.toList());
        }
        return bookList;
    }
}
