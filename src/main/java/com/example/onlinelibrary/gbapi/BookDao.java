package com.example.onlinelibrary.gbapi;

import com.example.onlinelibrary.domain.Book;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.google.common.collect.ImmutableList;
import javafx.beans.NamedArg;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    @Autowired
    private BookApiClient bookApiClient;
    private int numOfBook;

    //@Cacheable("books")
    public List<Book> findByTitle(String title, Long page) {
        Volumes volumes = bookApiClient.findByTitle(title, page);

        if (volumes != null) {
            numOfBook = volumes.getTotalItems();
            return volumes.getItems().stream().map(volume -> Book.builder()
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
                    .saleability(
                            "NOT_FOR_SALE".equals(volume.getSaleInfo().getSaleability()))
                    .categories(
                            Optional.ofNullable(volume.getVolumeInfo().getCategories()).orElse(new ArrayList<>()))
                    .description(
                            Optional.ofNullable(volume.getVolumeInfo().getDescription()).orElse("..."))
                    .thumbnailUrl(
                            Optional.ofNullable(
                                    volume.getVolumeInfo().getImageLinks())
                                    .orElse(new Volume.VolumeInfo.ImageLinks())
                                    .getThumbnail())
                    .webReaderLink(Optional.ofNullable(volume.getAccessInfo().getWebReaderLink()).orElse("..."))
                    .build()
            ).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public int getNumberOfBook() {
        return numOfBook;
    }

    public List<Book> findByAuthor(String author) {
        return null;
    }

    public Book findByIsbn() {
        return null;
    }
}
