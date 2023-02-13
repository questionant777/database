package ru.otus.spring.repository;

import ru.otus.spring.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentJpa {

    BookComment save(BookComment BookComment);

    Optional<BookComment> findById(long id);

    List<BookComment> findAll();

    List<BookComment> findByBookId(long bookid);

    void deleteById(long id);

}
