package com.example.onlinelibrary.gbapi.gbook;

import lombok.Data;

@Data
public class GoogleBook {
    private String id;
    private String etag;
    private VolumeInfo volumeInfo;
}