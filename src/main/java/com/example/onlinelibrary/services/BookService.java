package com.example.onlinelibrary.services;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.gbapi.GoogleBooksApiClient;
import com.example.onlinelibrary.gbapi.gbook.GoogleBook;
import com.example.onlinelibrary.gbapi.gbook.ImageLinks;
import com.example.onlinelibrary.gbapi.gbook.VolumeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookService {
    @Autowired
    private GoogleBooksApiClient bookApiClient;

    @Cacheable("books")
    public List<Book> findByTitle(Query query) {
        List<GoogleBook> result = bookApiClient.executeQuery(query);

        if (result != null) {
            return result.stream().map(volume -> Book.builder()
                    .title(
                            Optional.ofNullable(volume.getVolumeInfo().getTitle()).orElse("..."))
                    .subtitle(
                            Optional.ofNullable(volume.getVolumeInfo().getSubtitle()).orElse("..."))
                    .authors(
                            Optional.ofNullable(volume.getVolumeInfo().getAuthors()).orElse(new ArrayList<>(0)))
                    .publisher(
                            Optional.ofNullable(volume.getVolumeInfo().getPublisher()).orElse("..."))
                    .publishedDate(
                            Optional.ofNullable(volume.getVolumeInfo().getPublishedDate()).orElse("..."))
                    .pageCount(
                            Optional.ofNullable(volume.getVolumeInfo().getPageCount()).orElse(0))
                    .averageRating(
                            Optional.ofNullable(volume.getVolumeInfo().getAverageRating()).orElse(0.0))
                    .language(
                            Optional.ofNullable(volume.getVolumeInfo().getLanguage()).orElse("..."))
                    .categories(
                            Optional.ofNullable(volume.getVolumeInfo().getCategories()).orElse(new ArrayList<>()))
                    .description(
                            Optional.ofNullable(volume.getVolumeInfo().getDescription()).orElse("..."))
                    .thumbnailUrl(
                            Optional.ofNullable(
                                    volume.getVolumeInfo().getImageLinks())
                                    .orElse(new ImageLinks())
                                    .getThumbnail())
                                    .build()
                    ).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<Book> findByAuthor(String author) {
        return null;
    }

    public Book findByIsbn() {
        return null;
    }
}
