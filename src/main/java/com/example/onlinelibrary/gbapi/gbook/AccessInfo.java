package com.example.onlinelibrary.gbapi.gbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessInfo {
    @JsonProperty("pdf")
    private Source pdf = new Source();
    @JsonProperty("epub")
    private Source epub = new Source();
    private String webReaderLink;

    @JsonSetter("pdf")
    public void setPdf(Source pdf) {
        if (pdf != null) {
            this.pdf = pdf;
        }
    }

    @JsonSetter("epub")
    public void setEpub(Source epub) {
        if (epub != null) {
            this.epub = epub;
        }
    }

    @JsonSetter("webReaderLink")
    public void setWebReaderLink(String webReaderLink) {
        if (webReaderLink != null) {
            this.webReaderLink = webReaderLink;
        }
    }
}
