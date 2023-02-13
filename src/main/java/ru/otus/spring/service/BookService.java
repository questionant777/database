package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
    Book save(String bookName, String authorName, String genreName);
    void deleteById(Long bookId);
    Book findById(Long bookId);
    Book findByName(String bookName);
    List<Book> findAll();
}
