package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorJpa {

    Author save(Author author);

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    List<Author> findAll();

    void deleteById(long id);
}
