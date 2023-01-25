package ru.otus.spring.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Genre extends LibraryBase {
    public Genre(Long id, String name) {
        super(id, name);
    }
}
