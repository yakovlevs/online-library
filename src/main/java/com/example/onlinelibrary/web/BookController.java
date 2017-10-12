package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.services.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j
@Controller
@Scope("session")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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
}
