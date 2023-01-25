package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.LibraryBase;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    int count();

    Genre insert(Genre genre);

    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);

    List<Genre> getAll();

    void deleteById(long id);
}
