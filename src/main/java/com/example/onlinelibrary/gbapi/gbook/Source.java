package com.example.onlinelibrary.gbapi.gbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Source {
    @JsonProperty("downloadLink")
    private String downloadLink = "";

    @JsonSetter("downloadLink")
    public void setDownloadLink(String downloadLink) {
        if (downloadLink != null) {
            this.downloadLink = downloadLink;
        }
    }
}
