package com.example.onlinelibrary.gbapi.gbook;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class VolumeInfo {
    private String title = "";
    private String subtitle = "";
    private List<String> authors = new ArrayList<>();
    private String publisher = "";
    private String publishedDate = "";
    private String description = "";
    private List<IndustryIdentifiers> industryIdentifiers = new ArrayList<>();
    private Integer pageCount = 0;
    private List<String> categories = new ArrayList<>();
    private Double averageRating = 0.0d;
    private String language = "";
    private ImageLinks imageLinks = new ImageLinks();

    public void setTitle(String title) {
        if (title != null) {
            this.title = title;
        }

    }

    public void setSubtitle(String subtitle) {
        if (subtitle != null) {
            this.subtitle = subtitle;
        }
    }

    public void setAuthors(List<String> authors) {
        if (authors != null) {
            this.authors = authors;
        }
    }

    public void setPublisher(String publisher) {
        if (publisher != null) {
            this.publisher = publisher;
        }
    }

    public void setPublishedDate(String publishedDate) {
        if (publishedDate != null) {
            this.publishedDate = publishedDate;
        }
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }

    }

    public void setIndustryIdentifiers(List<IndustryIdentifiers> industryIdentifiers) {
        if (industryIdentifiers != null) {
            this.industryIdentifiers = industryIdentifiers;
        }
    }

    public void setPageCount(Integer pageCount) {
        if (pageCount != null) {
            this.pageCount = pageCount;
        }
    }

    public void setCategories(List<String> categories) {
        if (categories != null) {
            this.categories = categories;
        }
    }

    public void setAverageRating(Double averageRating) {
        if (averageRating != null) {
            this.averageRating = averageRating;
        }
    }

    public void setLanguage(String language) {
        if (language != null) {
            this.language = language;
        }
    }

    public void setImageLinks(ImageLinks imageLinks) {
        if (imageLinks != null) {
            this.imageLinks = imageLinks;
        }
    }
}