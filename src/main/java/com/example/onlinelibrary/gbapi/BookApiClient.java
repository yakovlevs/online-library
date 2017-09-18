package com.example.onlinelibrary.gbapi;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Log4j
@Component
public class BookApiClient {
    //Client credentials
    private static final String API_KEY = "AIzaSyCsLojOS0xDrZPm7TQLkmyWv1CTB5mniOc";
    private static final String APPLICATION_NAME = "Education project";
    private Books books;

    @PostConstruct
    public void init() {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        try {
            // Set up Books client.
            books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            log.error("Failed to create google books api client", e);
        }
    }

    private Volumes searchByQuery(@NonNull String query, @NonNull Long page) {
        Volumes volumes = null;
        try {
            // Set query string and filters.
            Books.Volumes.List volumesList = books.volumes().list(query);
            volumesList.setFilter("ebooks");
            volumesList.setMaxResults(20L);
            volumesList.setStartIndex(volumesList.getMaxResults() * page);

            // Execute the query.
            volumes = volumesList.execute();
            if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return volumes;
    }

    public Volumes findByTitle(@NonNull String query, @NonNull Long page) {
        return searchByQuery("intitle:" + query, page);
    }
}
