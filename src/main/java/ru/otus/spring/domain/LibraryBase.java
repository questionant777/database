package ru.otus.spring.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class LibraryBase {
    public LibraryBase(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    private Long id;
    private String name;
}
