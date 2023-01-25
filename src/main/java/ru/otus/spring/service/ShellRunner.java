package ru.otus.spring.service;

import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.stream.Collectors;

@Component
@Profile("!test")
@ShellComponent
public class ShellRunner {
    private final HandleInOut handleInOut;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ShellRunner(HandleInOut handleInOut, BookService bookService, AuthorService authorService, GenreService genreService) {
        this.handleInOut = handleInOut;
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(value = "Insert book", key = {"ib", "insb", "insert book"})
    public void insertBook() {
        Book book = fillBookParamsFromConsole();
        Book savedBook = bookService.insert(book);

        handleInOut.outAndCr("Добавлена книга: " + savedBook.toString());
    }

    private Book fillBookParamsFromConsole() {
        handleInOut.out("Наименование книги: ");
        String bookName = handleInOut.in();

        handleInOut.out("ФИО автора: ");
        String authorName = handleInOut.in();

        handleInOut.out("Жанр: ");
        String genreName = handleInOut.in();

        Author author = authorService.getOrSaveByName(authorName);

        Genre genre = genreService.getOrSaveByName(genreName);

        System.out.println(author
        );
        return new Book(
                null,
                bookName,
                author.getId(),
                genre.getId()
        );
    }

    @ShellMethod(value = "Delete book by Id", key = {"delb"})
    public void deleteBookById() {
        bookService.deleteById(deleteBookParamsFromConsole());
    }

    private Long deleteBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для удаления: ");
        return Long.valueOf(handleInOut.in());
    }

    @ShellMethod(value = "Get book by Id", key = {"getb"})
    public void getBookById() {
        handleInOut.outAndCr(bookService.getById(getBookParamsFromConsole()).toString());
    }

    private Long getBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для чтения: ");
        return Long.valueOf(handleInOut.in());
    }

    @ShellMethod(value = "Print all book", key = {"pab", "allb"})
    public void printAllBook() {
        handleInOut.outAndCr("Все книги бибилиотеки: ");
        handleInOut.outAndCr(bookService.getAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"))
        );
    }

}
