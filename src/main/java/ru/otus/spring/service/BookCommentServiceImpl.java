package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.exception.BookCommentNotFoundException;
import ru.otus.spring.repository.BookCommentJpa;

import java.util.List;
import java.util.Optional;

@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentJpa bookCommentJpa;
    private final BookService bookService;

    public BookCommentServiceImpl(BookCommentJpa bookCommentJpa, BookService bookService) {
        this.bookCommentJpa = bookCommentJpa;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public BookComment update(BookComment bookComment) {
        Long bookCommentId = bookComment.getId();

        Optional<BookComment> foundBookCommentOpt = bookCommentJpa.findById(bookCommentId);

        if (foundBookCommentOpt.isPresent()) {
            bookComment.setBook(foundBookCommentOpt.get().getBook());
        } else {
            throw new BookCommentNotFoundException(bookCommentId);
        }

        return bookCommentJpa.save(bookComment);
    }

    @Transactional
    @Override
    public BookComment insert(BookComment bookComment) {
        if (bookComment.getId() != null && bookComment.getId() != 0)
            throw new RuntimeException("При добавлении комментария идентификатор должен быть пустым");

        Book book = Optional.ofNullable(bookComment.getBook()).orElse(new Book());

        Book foundBook;

        if (book.getId() == null) {
            foundBook = bookService.findByName(book.getName());
        } else {
            foundBook = bookService.findById(book.getId());
        }

        bookComment.setBook(foundBook);

        return bookCommentJpa.save(bookComment);
    }

    @Transactional
    @Override
    public void deleteById(Long bookCommentId) {
        bookCommentJpa.deleteById(bookCommentId);
    }

    @Override
    public BookComment findById(Long bookCommentId) {
        Optional<BookComment> bookCommentOpt = bookCommentJpa.findById(bookCommentId);
        if (bookCommentOpt.isPresent())
            return bookCommentOpt.get();
        else
            throw new BookCommentNotFoundException(bookCommentId);
    }

    @Override
    public List<BookComment> findByBookId(Long bookId) {
        bookService.findById(bookId);
        return bookCommentJpa.findByBookId(bookId);
    }

    @Override
    public List<BookComment> findAll() {
        return bookCommentJpa.findAll();
    }
}
