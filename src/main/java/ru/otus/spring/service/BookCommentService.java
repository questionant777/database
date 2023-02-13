package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

import java.util.List;

public interface BookCommentService {
    BookComment save(BookComment bookComment);
    void deleteById(Long bookCommentId);
    BookComment findById(Long bookCommentId);
    List<BookComment> findByBookId(Long bookId);
    List<BookComment> findAll();
}
