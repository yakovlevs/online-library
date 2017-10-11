package com.example.onlinelibrary.services;

import com.example.onlinelibrary.domain.Book;
import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.gbapi.GoogleBooksApiClient;
import com.example.onlinelibrary.gbapi.gbook.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookService {

    @Autowired
    private GoogleBooksApiClient bookApiClient;

    @Cacheable("listBook")
    public List<Book> findByTitle(Query query) {
        List<GoogleBook> result = bookApiClient.executeQuery(query);
        if (result != null) return result.stream().map(this::convertGoogleBook).collect(Collectors.toList());
        return null;
    }

    @Cacheable("singleBook")
    public Book findByGoogleId(Query query) {
        return convertGoogleBook(bookApiClient.executeQuery(query).get(0));
    }

    private Book convertGoogleBook(@NonNull GoogleBook volume) {
        return Book.builder()
                .id(volume.getId())
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
                .saleability(volume.getSaleInfo().getSaleability().equals("FOR_SALE"))
                .price(volume.getSaleInfo().getRetailPrice().getAmount())
                .currencyCode(volume.getSaleInfo().getRetailPrice().getCurrencyCode())
                .signatureValue("")
                .build();
    }



}
