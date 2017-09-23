package com.example.onlinelibrary.domain;

import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Query {
    private String request;
    private int startIndex;
    private int maxResults = 10;

    private Query() {
    }

    public String getFullQuery() {
        String requiredFields = "items(id,etag," +
                "volumeInfo(" +
                "title,subtitle,authors,publisher,publishedDate," +
                "description,pageCount,categories,averageRating," +
                "language,industryIdentifiers,imageLinks(thumbnail)))";
        String result ="";
        if (request != null) result += "q=" + encodeToUtf8(request);
        result += "&startIndex=" + startIndex;
        result += "&maxResults=" + maxResults;
        result += "&fields=" + encodeToUtf8(requiredFields);
        return result;
    }

    private String encodeToUtf8(@NonNull String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Builder builder() {
        return new Query().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setTitle(@NonNull String request) {
            Query.this.request = request;
            return this;
        }

        public Builder setStartIndex(@NonNull int index) {
            Query.this.startIndex = index;
            return this;
        }

        public Builder setMaxResult(@NonNull int results) {
            Query.this.maxResults = results;
            return this;
        }

        public Query build() {
            return Query.this;
        }
    }

}
