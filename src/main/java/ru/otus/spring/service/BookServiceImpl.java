package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.BookJpa;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookJpa bookJpa;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookJpa bookJpa, AuthorService authorService, GenreService genreService) {
        this.bookJpa = bookJpa;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Transactional
    @Override
    public Book save(String bookName, String authorName, String genreName) {
        Author author = authorService.getOrSaveByName(authorName);
        Genre genre = genreService.getOrSaveByName(genreName);

        Book savedBook = bookJpa.save(Book.builder()
                .name(bookName)
                .author(author)
                .genre(genre)
                .build());

        return savedBook;
    }

    @Override
    public void deleteById(Long bookId) {
        bookJpa.deleteById(bookId);
    }

    @Override
    public Book findById(Long bookId) {
        Optional<Book> bookOpt = bookJpa.findById(bookId);
        if (bookOpt.isPresent())
            return bookOpt.get();
        else
            throw new BookNotFoundException(bookId);
    }

    @Override
    public Book findByName(String bookName) {
        Optional<Book> bookOpt = bookJpa.findByName(bookName);
        if (bookOpt.isPresent())
            return bookOpt.get();
        else
            throw new BookNotFoundException(bookName);
    }

    @Transactional
    @Override
    public List<Book> findAll() {
        return bookJpa.findAll();
    }
}
