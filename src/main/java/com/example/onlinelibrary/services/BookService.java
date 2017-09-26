package com.example.onlinelibrary.services;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.gbapi.GoogleBooksApiClient;
import com.example.onlinelibrary.gbapi.gbook.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                    .title(volume.getVolumeInfo().getTitle())
                    .subtitle(volume.getVolumeInfo().getSubtitle())
                    .authors(volume.getVolumeInfo().getAuthors())
                    .publisher(volume.getVolumeInfo().getPublisher())
                    .publishedDate(volume.getVolumeInfo().getPublishedDate())
                    .pageCount(volume.getVolumeInfo().getPageCount())
                    .averageRating(volume.getVolumeInfo().getAverageRating())
                    .language(volume.getVolumeInfo().getLanguage())
                    .categories(volume.getVolumeInfo().getCategories())
                    .description(volume.getVolumeInfo().getDescription())
                    .thumbnailUrl(volume.getVolumeInfo().getImageLinks().getThumbnail())
                    .pdfLink(volume.getAccessInfo().getPdf().getDownloadLink())
                    .epubLink(volume.getAccessInfo().getEpub().getDownloadLink())
                    .webReaderLink(volume.getAccessInfo().getWebReaderLink())
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
