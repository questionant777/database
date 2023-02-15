package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.exception.BookCommentNotFoundException;
import ru.otus.spring.repository.BookCommentRepository;
import ru.otus.spring.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;
    private final BookService bookService;

    public BookCommentServiceImpl(BookCommentRepository bookCommentRepository, BookService bookService) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public BookComment save(BookComment bookComment) {
        Book book = Optional.ofNullable(bookComment.getBook()).orElse(new Book());

        Book foundBook;

        if (book.getId() == null) {
            foundBook = bookService.findByName(book.getName());
        } else {
            foundBook = bookService.findById(book.getId());
        }

        bookComment.setBook(foundBook);

        return bookCommentRepository.save(bookComment);
    }

    @Override
    public void deleteById(Long bookCommentId) {
        bookCommentRepository.deleteById(bookCommentId);
    }

    @Override
    public BookComment findById(Long bookCommentId) {
        Optional<BookComment> bookCommentOpt = bookCommentRepository.findById(bookCommentId);
        if (bookCommentOpt.isPresent())
            return bookCommentOpt.get();
        else
            throw new BookCommentNotFoundException(bookCommentId);
    }

    @Override
    public List<BookComment> findByBookId(Long bookId) {
        bookService.findById(bookId);
        return bookCommentRepository.findByBookId(bookId);
    }

    @Override
    public List<BookComment> findAll() {
        return bookCommentRepository.findAll();
    }
}
