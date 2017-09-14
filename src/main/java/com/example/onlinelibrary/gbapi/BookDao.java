package com.example.onlinelibrary.gbapi;

import com.example.onlinelibrary.domain.Book;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    @Autowired
    private BookApiClient bookApiClient;
    @Cacheable("books")
    public List<Book> findByTitle(String title) {
        Volumes volumes = bookApiClient.findByTitle(title);
        if (volumes != null) {
            return volumes.getItems().stream().map(volume -> new Book(
                    volume.getVolumeInfo().getTitle(),
                    volume.getVolumeInfo().getSubtitle(),
                    volume.getVolumeInfo().getAuthors(),
                    volume.getVolumeInfo().getPublisher(),
                    volume.getVolumeInfo().getPublishedDate(),
                    volume.getVolumeInfo().getPageCount(),
                    volume.getVolumeInfo().getAverageRating(),
                    volume.getVolumeInfo().getLanguage(),
                    volume.getSaleInfo().getSaleability().equals("NOT_FOR_SALE"),
                    volume.getVolumeInfo().getCategories(),
                    volume.getVolumeInfo().getDescription(),
                    volume.getVolumeInfo().getImageLinks().getThumbnail(),
                    volume.getAccessInfo().getWebReaderLink()
            )).collect(Collectors.toList());
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
