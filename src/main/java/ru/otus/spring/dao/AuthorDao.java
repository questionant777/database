package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    int count();

    Author insert(Author author);

    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getAll();

    void deleteById(long id);
}
