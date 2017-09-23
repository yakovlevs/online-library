package com.example.onlinelibrary.gbapi.gbook;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private List<Map<String, String>> industryIdentifiers;
    private Integer pageCount;
    private List<String> categories;
    private Double averageRating;
    private String language;
    private ImageLinks imageLinks;
}