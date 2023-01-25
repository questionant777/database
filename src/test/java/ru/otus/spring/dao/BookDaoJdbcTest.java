package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 1;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_NAME = "Puaro";
    private static final Long EXISTING_BOOK_AUTHOR_ID = 1L;
    private static final Long EXISTING_BOOK_GENRE_ID = 2L;

    @Autowired
    private BookDao bookDao;

    @Test
    void countTest() {
        int actualPersonsCount = bookDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    void insertTest() {
        Book newBook = new Book(null, "Twain", EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        Book savedBook = bookDao.insert(newBook);
        Book actualBook = bookDao.getById(savedBook.getId()).orElse(new Book());
        assertThat(actualBook).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newBook);
    }

    @Test
    void getByIdExistingBookTest() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID).orElse(new Book());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void deleteByIdOkTest() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        Optional<Book> BookOpt = bookDao.getById(EXISTING_BOOK_ID);

        assertThat(BookOpt.isPresent()).isFalse();
    }

    @Test
    void getAllTest() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_BOOK_AUTHOR_ID, EXISTING_BOOK_GENRE_ID);
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

}