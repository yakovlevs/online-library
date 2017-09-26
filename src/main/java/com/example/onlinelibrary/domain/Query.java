package com.example.onlinelibrary.domain;

import lombok.NonNull;
import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
@ToString
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
                "language,industryIdentifiers,imageLinks(thumbnail))," +
                "accessInfo(epub(downloadLink),pdf(downloadLink),webReaderLink))";
        String result ="";
        if (request != null) result += "q=" + encodeToUtf8(request);
        result += "&startIndex=" + startIndex;
        result += "&maxResults=" + maxResults;
        result += "&fields=" + encodeToUtf8(requiredFields);
        result += "&filter=ebooks";
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

        public Builder setStartIndex(int index) {
            Query.this.startIndex = index;
            return this;
        }

        public Builder setMaxResult(int results) {
            Query.this.maxResults = results;
            return this;
        }

        public Query build() {
            return Query.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query = (Query) o;

        if (startIndex != query.startIndex) return false;
        if (maxResults != query.maxResults) return false;
        return request.equals(query.request);
    }

    @Override
    public int hashCode() {
        int result = request.hashCode();
        result = 31 * result + startIndex;
        result = 31 * result + maxResults;
        return result;
    }
}
