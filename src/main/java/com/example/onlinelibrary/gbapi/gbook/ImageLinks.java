package com.example.onlinelibrary.gbapi.gbook;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageLinks {
    private String thumbnail = "";

    public void setThumbnail(String thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = thumbnail;
        }
    }
}
