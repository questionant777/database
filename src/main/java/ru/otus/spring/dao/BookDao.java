package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.LibraryBase;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();

    Book insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
