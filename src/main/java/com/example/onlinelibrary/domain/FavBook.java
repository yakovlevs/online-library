package com.example.onlinelibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavBook {
    @Id
    @GeneratedValue
    private Long id;
    private String googleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavBook books = (FavBook) o;
        return googleId.equals(books.googleId);
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + googleId.hashCode();
        return result;
    }
}
