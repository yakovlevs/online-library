package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.payment.Robokassa;
import com.example.onlinelibrary.persistence.UserDao;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    private final Robokassa robokassa;
    private User user;

    @Autowired
    public UserSession(Robokassa robokassa) {
        this.robokassa = robokassa;
    }


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

    public List<Book> updateBookStatus(List<Book> bookList, User user) {
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
