package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {

    public static final long NEW_BOOK_ID = 1L;
    public static final String BOOK_NAME = "any book name";
    public static final long AUTHOR_ID = 2L;
    public static final long GENRE_ID = 3L;
    public static final long DELETE_BOOK_ID = 4L;
    public static final long EXISTING_BOOK_ID = 5L;
    public static final long NOT_EXISTING_BOOK_ID = 6L;
    @Mock
    BookDao bookDao;

    @InjectMocks
    BookServiceImpl service;

    private Book getAnyBook(Long bookId) {
        return new Book(
                bookId,
                BOOK_NAME,
                AUTHOR_ID,
                GENRE_ID
        );
    }

    @Test
    void insertTest() {
        when(bookDao.insert(any()))
                .thenReturn(getAnyBook(NEW_BOOK_ID));

        Book newBook = getAnyBook(NEW_BOOK_ID);

        Book afterInsBook = service.insert(newBook);

        assertThat(afterInsBook).usingRecursiveComparison().isEqualTo(newBook);
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(DELETE_BOOK_ID);
        verify(bookDao, times(1)).deleteById(DELETE_BOOK_ID);
    }

    @Test
    void getByIdExistingBookIdTest() {
        Book expectedBook = getAnyBook(EXISTING_BOOK_ID);

        when(bookDao.getById(EXISTING_BOOK_ID))
                .thenReturn(Optional.of(expectedBook));

        Book actualBook = service.getById(EXISTING_BOOK_ID);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void getByIdNotExistingBookIdExceptionTest() {
        assertThatCode(() -> service.getById(NOT_EXISTING_BOOK_ID))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void getAllTest() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(getAnyBook(EXISTING_BOOK_ID));

        when(bookDao.getAll())
                .thenReturn(bookList);

        Book expectedBook = getAnyBook(EXISTING_BOOK_ID);

        List<Book> actualBookList = service.getAll();

        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }
}