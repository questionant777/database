package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    Book insert(Book book);
    void deleteById(Long bookId);
    Book getById(Long bookId);
    List<Book> getAll();
}
