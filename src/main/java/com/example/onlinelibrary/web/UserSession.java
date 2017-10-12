package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.persistence.UserDao;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Data
@Component
@Scope("session")
public class UserSession {
    private String lastSearchQuery = "";
    private int booksOnPage = 20;
    private int currentPage = 0;
    private int numOfBooks = 0;
    private List<Book> lastSearchResult;
    private User user;

    public void updateUser(UserDao userDao) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().toString().equals("anonymousUser")) {
            try {
                User u = (User) authentication.getPrincipal();
                user = userDao.findByUserName(u.getUsername()).orElse(null);
            } catch (ClassCastException ex) {
                log.error(ex);
            }
        } else {
            user = null;
        }
    }
}
