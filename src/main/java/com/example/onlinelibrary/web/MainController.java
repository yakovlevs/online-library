package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Role;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.gbapi.BookDao;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Log4j
@Controller
public class MainController {
    @Autowired
    private BookDao bookDao;
    private List<Book> lastSearchResult;

    @GetMapping({"/", "home"})
    public String getHome(Model model) {
        model.addAttribute("username", "");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            try {
                User user = (User) authentication.getPrincipal();
                model.addAttribute("username", user.getUsername());
            } catch (ClassCastException ex) {
                log.info(ex);
            }
        }
        model.addAttribute("searchResult", lastSearchResult);
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
        }
        return "login";
    }

    @GetMapping("/content")
    public String getContent(@RequestParam(value = "query", required = true) String query, Model model) {
        List<Book> result = bookDao.findByTitle(query);
        lastSearchResult = result;
        if (result != null) {
            model.addAttribute("searchResult", result);
            //log.info(Arrays.toString(result.toArray()));
        }
        return "content";
    }
}
