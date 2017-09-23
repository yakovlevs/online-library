package com.example.onlinelibrary.gbapi;

import com.example.onlinelibrary.gbapi.gbook.GoogleBook;
import lombok.Data;

import java.util.List;
@Data
public class GoogleBooksApiResponse {
    private List<GoogleBook> items;

    public List<GoogleBook> getItems() {
        return items;
    }
}
