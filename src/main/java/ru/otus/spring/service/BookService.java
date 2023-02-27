package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    Book insert(Book book);
    Book update(Book book);
    void deleteById(Long bookId);
    Book findById(Long bookId);
    Book findByName(String bookName);
    List<Book> findAll();
}
