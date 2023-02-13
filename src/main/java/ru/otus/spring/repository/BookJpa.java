package ru.otus.spring.repository;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookJpa {

    Book save(Book Book);

    Optional<Book> findById(long id);

    Optional<Book> findByName(String name);

    List<Book> findAll();

    void deleteById(long id);
}
