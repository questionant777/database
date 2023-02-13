package ru.otus.spring.service;

import org.springframework.context.annotation.Profile;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookComment;

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
        handleInOut.out("Наименование книги: ");
        String bookName = handleInOut.in();

        handleInOut.out("ФИО автора: ");
        String authorName = handleInOut.in();

        handleInOut.out("Жанр: ");
        String genreName = handleInOut.in();

        Book savedBook = bookService.save(bookName, authorName, genreName);
        handleInOut.outAndCr("Добавлена книга: " + savedBook.toString());
    }

    @ShellMethod(value = "Delete book by Id", key = {"delb"})
    public void deleteBookById() {
        bookService.deleteById(deleteBookParamsFromConsole());
    }

    private Long deleteBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для удаления: ");
        return Long.valueOf(handleInOut.in());
    }

    @Transactional
    @ShellMethod(value = "Get book by Id", key = {"getb"})
    public void getBookById() {
        handleInOut.outAndCr(bookService.findById(getBookParamsFromConsole()).toString());
    }

    private Long getBookParamsFromConsole() {
        handleInOut.out("Идентификатор книги для чтения: ");
        return Long.valueOf(handleInOut.in());
    }

    @Transactional
    @ShellMethod(value = "Print all book", key = {"pab", "allb"})
    public void printAllBook() {
        handleInOut.outAndCr("Все книги бибилиотеки: ");
        handleInOut.outAndCr(bookService.findAll()
                .stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n"))
        );
    }

    @Transactional
    @ShellMethod(value = "Comment book", key = {"ic", "addc"})
    public void commentBook() {
        handleInOut.out("Комментировать книгу по идентификатору: ");
        Long bookId = Long.valueOf(handleInOut.in());

        handleInOut.out("Комментарий: ");
        String comment = handleInOut.in();

        BookComment bookComment = BookComment.builder()
                .book(Book.builder().id(bookId).build())
                .comment(comment)
                .build();

        BookComment savedBookCom = bookCommentService.save(bookComment);

        handleInOut.outAndCr("Добавлен комментарий: " + savedBookCom.toString());
    }

    @Transactional
    @ShellMethod(value = "Print comment by book", key = {"com"})
    public void printCommentByBook() {
        handleInOut.out("Печать комментарии к книге по идентификатору: ");
        Long bookId = Long.valueOf(handleInOut.in());

        List<BookComment> bookCommentList = bookCommentService.findByBookId(bookId);

        bookCommentList.forEach(c -> handleInOut.out(c.toString()));
    }

}
