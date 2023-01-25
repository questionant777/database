package ru.otus.spring.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Author extends LibraryBase {
    public Author(Long id, String name) {
        super(id, name);
    }
}
