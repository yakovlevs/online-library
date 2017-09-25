package com.example.onlinelibrary.gbapi.gbook;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IndustryIdentifiers {
    private String type = "";
    private String identifier = "";

    public void setType(String type) {
        if (type != null) {
            this.type = type;
        }
    }

    public void setIdentifier(String identifier) {
        if (identifier != null) {
            this.identifier = identifier;
        }
    }
}
