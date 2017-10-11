package com.example.onlinelibrary.domain;

import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Query {
    private String request;
    private String language;
    private String filter;
    private String download;
    private String printType;
    private String order;
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
                "accessInfo(epub(downloadLink),pdf(downloadLink),webReaderLink)," +
                "saleInfo(saleability,retailPrice))";
        String result = "";
        if (request != null) result += "q=" + encodeToUtf8(request);
        if (language != null) result += "&langRestrict=" + language;
        if (filter != null) result += "&filter=" + filter;
        if (download != null) result += "&download=" + download;
        if (printType != null) result += "&printType=" + printType;
        if (order != null) result += "&orderBy=" + order;
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

        public Builder setLanguage(@NonNull String language) {
            Query.this.language = language;
            return this;
        }

        public Builder setFilter(@NonNull String filter) {
            Query.this.filter = filter;
            return this;
        }

        public Builder setPrintType(@NonNull String printType) {
            Query.this.printType = printType;
            return this;
        }
        public Builder setOrder(@NonNull String order) {
            Query.this.order = order;
            return this;
        }

        public Builder setStartIndex(int index) {
            Query.this.startIndex = index;
            return this;
        }

        public Builder setDownloadable(boolean downloadable) {
            if (downloadable) {
                Query.this.download = "epub";
            }
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
        if (request != null ? !request.equals(query.request) : query.request != null) return false;
        if (language != null ? !language.equals(query.language) : query.language != null) return false;
        if (filter != null ? !filter.equals(query.filter) : query.filter != null) return false;
        if (download != null ? !download.equals(query.download) : query.download != null) return false;
        if (printType != null ? !printType.equals(query.printType) : query.printType != null) return false;
        return order != null ? order.equals(query.order) : query.order == null;
    }

    @Override
    public int hashCode() {
        int result = request != null ? request.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (filter != null ? filter.hashCode() : 0);
        result = 31 * result + (download != null ? download.hashCode() : 0);
        result = 31 * result + (printType != null ? printType.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + startIndex;
        result = 31 * result + maxResults;
        return result;
    }
}
