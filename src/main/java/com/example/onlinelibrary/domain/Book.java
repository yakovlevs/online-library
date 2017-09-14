package com.example.onlinelibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private Integer pageCount;
    private Double averageRating;
    private String language;
    private Boolean saleability;
    private List<String> categories;
    private String description;
    private String thumbnailUrl;
    private String webReaderLink;
}
