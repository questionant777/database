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
    public Book insert(Book book) {
        if (book.getId() != null && book.getId() != 0)
            throw new RuntimeException("При добавлении книги идентификатор должен быть пустым");
        return save(book);
    }

    @Transactional
    @Override
    public Book update(Book book) {
        Long bookId = book.getId();
        Long cnt = bookJpa.countById(bookId);
        if (cnt.equals(0L)) {
            throw new BookNotFoundException(bookId);
        }
        return save(book);
    }

    private Book save(Book book) {
        Author author = Optional.ofNullable(book.getAuthor()).orElse(new Author());
        Genre genre = Optional.ofNullable(book.getGenre()).orElse(new Genre());

        if (author.getId() == null || author.getId() == 0)
            author = authorService.getOrSaveByName(author.getName());

        if (genre.getId() == null || genre.getId() == 0)
            genre = genreService.getOrSaveByName(genre.getName());

        return bookJpa.save(Book.builder()
                .id(book.getId())
                .name(book.getName())
                .author(author)
                .genre(genre)
                .build());
    }

    @Transactional
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

    @Override
    public List<Book> findAll() {
        return bookJpa.findAll();
    }

}
