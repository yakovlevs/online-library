package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Role;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.gbapi.BookDao;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
public class MainController {
    @Autowired
    private BookDao bookDao;
    private String lastSearchQuery = "";
    private int booksOnPage = 20;
    private long currentPage = 0L;

    @GetMapping({"/", "home"})
    public String getHome(Model model) {
        model.addAttribute("username", "");
        model.addAttribute("search", lastSearchQuery);
        model.addAttribute("booksOnPage", booksOnPage);
        model.addAttribute("numOfBooks", bookDao.getNumberOfBook());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            try {
                User user = (User) authentication.getPrincipal();
                model.addAttribute("username", user.getUsername());
            } catch (ClassCastException ex) {
                log.info(ex);
            }
        }
        model.addAttribute("searchResult", bookDao.findByTitle(lastSearchQuery, currentPage));
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
            /*@RequestParam(value = "count", required = false) String count,*/
            Model model) {
        if (page != null) {
            currentPage = Long.parseLong(page);
        }
        List<Book> result = bookDao.findByTitle(query, currentPage);
        model.addAttribute("booksOnPage", booksOnPage);
        model.addAttribute("numOfBooks", bookDao.getNumberOfBook());
        lastSearchQuery = query;
        if (result != null) {
            model.addAttribute("searchResult", result);
            log.info("user request: " + query +
                    " page: " + page);
        }
        return "content";
    }
}
