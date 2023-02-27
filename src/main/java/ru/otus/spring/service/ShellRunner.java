package ru.otus.spring.service;

import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Profile("!test")
@ShellComponent
public class ShellRunner {
    private final HandleInOut handleInOut;
    private final BookService bookService;
    private final BookCommentService bookCommentService;

    public ShellRunner(HandleInOut handleInOut, BookService bookService, BookCommentService bookCommentService) {
        this.handleInOut = handleInOut;
        this.bookService = bookService;
        this.bookCommentService = bookCommentService;
    }

    @ShellMethod(value = "Insert book", key = {"ib", "insb", "insert book"})
    public void insertBook() {
        Book book = fillBookParamsFromConsole();
        Book savedBook = bookService.insert(book);
        handleInOut.outAndVk("Добавлена книга: " + savedBook.toString());
    }

    private Book fillBookParamsFromConsole() {
        handleInOut.out("Наименование книги: ");
        String bookName = handleInOut.in();

        handleInOut.out("ФИО автора: ");
        String authorName = handleInOut.in();

        handleInOut.out("Жанр: ");
        String genreName = handleInOut.in();

        return new Book(
                null,
                bookName,
                new Author(null, authorName),
                new Genre(null, genreName),
                null
        );
    }

    @ShellMethod(value = "Update book", key = {"updb"})
    public void updateBook() {
        handleInOut.out("Обновление книги по идентификатору: ");
        Long bookid = Long.valueOf(handleInOut.in());

        Book book = fillBookParamsFromConsole();

        book.setId(bookid);

        Book savedBook = bookService.update(book);

        handleInOut.outAndVk("Обновлена книга: " + savedBook.toString());
    }

    @ShellMethod(value = "Delete book by Id", key = {"delb"})
    public void deleteBookById() {
        bookService.deleteById(deleteBookParamsFromConsole());
    }

    private Long deleteBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для удаления: ");
        return Long.valueOf(handleInOut.in());
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Get book by Id", key = {"getb"})
    public void getBookById() {
        handleInOut.outAndVk(bookService.findById(getBookParamsFromConsole()).toString());
    }

    private Long getBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для чтения: ");
        return Long.valueOf(handleInOut.in());
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Print all book", key = {"pab", "allb"})
    public void printAllBook() {
        handleInOut.outAndVk("Все книги бибилиотеки: ");
        handleInOut.outAndVk(bookService.findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"))
        );
    }

    @ShellMethod(value = "Insert Comment book", key = {"ic", "insc"})
    public void insertCommentBook() {
        handleInOut.out("Добавить комментарий к книге по идентификатору: ");
        Long bookId = Long.valueOf(handleInOut.in());

        handleInOut.out("Комментарий: ");
        String comment = handleInOut.in();

        BookComment bookComment = BookComment.builder()
                .book(Book.builder().id(bookId).build())
                .comment(comment)
                .build();

        BookComment savedBookCom = bookCommentService.insert(bookComment);

        handleInOut.outAndVk("Добавлен комментарий: " + savedBookCom.getId() + ", " + savedBookCom.getComment());
    }

    @ShellMethod(value = "Update comment book", key = {"updc"})
    public void updateCommentBook() {
        handleInOut.out("Обновить комментарий по идентификатору: ");
        Long bookCommentId = Long.valueOf(handleInOut.in());

        handleInOut.out("Комментарий: ");
        String comment = handleInOut.in();

        BookComment bookComment = BookComment.builder()
                .id(bookCommentId)
                .comment(comment)
                .build();

        BookComment savedBookCom = bookCommentService.update(bookComment);

        handleInOut.outAndVk("Обновлен комментарий: " + savedBookCom.getId() + ", " + savedBookCom.getComment());
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Print comment by book", key = {"com"})
    public void printCommentByBook() {
        handleInOut.out("Печать комментарии к книге по идентификатору: ");
        Long bookId = Long.valueOf(handleInOut.in());

        List<BookComment> bookCommentList = bookCommentService.findByBookId(bookId);

        bookCommentList.forEach(c -> handleInOut.out(c.toString()));
    }

    @ShellMethod(value = "Delete comment book", key = {"delc"})
    public void deleteCommentBook() {
        handleInOut.out("Удалить комментарий по идентификатору: ");
        Long bookCommentId = Long.valueOf(handleInOut.in());

        bookCommentService.deleteById(bookCommentId);

        handleInOut.outAndVk("Удален комментарий: " + bookCommentId);
    }

}
