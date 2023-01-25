package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    public Book(Long id, String name, Long authorId, Long genreId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;

    }
    private Long id;
    private String name;
    private Long authorId;
    private Long genreId;
}
