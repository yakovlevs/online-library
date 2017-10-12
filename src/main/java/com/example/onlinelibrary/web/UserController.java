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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
@Scope("session")
public class UserController {
    private final UserDao userDao;
    private final Robokassa robokassa;
    private final BookService bookService;
    private final UserSession us;

    @Autowired
    public UserController(UserDao userDao, Robokassa robokassa, BookService bookService, UserSession us) {
        this.userDao = userDao;
        this.robokassa = robokassa;
        this.bookService = bookService;
        this.us = us;
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
            model.addAttribute("favorites", us.updateBookStatus(favoriteBooks, us.getUser()));
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
            model.addAttribute("purchased", us.updateBookStatus(purchasedBooks, us.getUser()));
            model.addAttribute("username", us.getUser().getUsername());
            model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        }
        return "user_purchased";
    }


}
