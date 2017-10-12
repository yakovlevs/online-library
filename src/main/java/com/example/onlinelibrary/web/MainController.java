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
            model.addAttribute("searchResult", us.updateBookStatus(result, us.getUser()));
        } else {
            model.addAttribute("searchResult", new ArrayList<Book>());
        }
        model.addAttribute("currentPage", us.getCurrentPage());
        model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        //Book book = new Book().getSignatureValue()
        return "home";
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
            model.addAttribute("searchResult", us.updateBookStatus(result, us.getUser()));
            model.addAttribute("MerchantLogin", robokassa.getMerchantLogin());
        }

        if (us.getUser() != null) {
            model.addAttribute("username", us.getUser().getUsername());
        }
        if (result == null) return "not_found";
        return "content";
    }


}
