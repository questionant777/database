package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreJpa {

    Genre save(Genre Genre);

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String name);

    List<Genre> findAll();

    void deleteById(long id);
}
