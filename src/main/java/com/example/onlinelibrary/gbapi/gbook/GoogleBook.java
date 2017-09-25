package com.example.onlinelibrary.gbapi.gbook;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleBook {
    private String id = "";
    private String etag = "";
    private VolumeInfo volumeInfo = new VolumeInfo();
    private AccessInfo accessInfo = new AccessInfo();

    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    public void setEtag(String etag) {
        if (etag != null) {
            this.etag = etag;
        }
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            this.volumeInfo = volumeInfo;
        }
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        if (accessInfo != null) {
            this.accessInfo = accessInfo;
        }
    }
}