package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book insert(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public void deleteById(Long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    public Book getById(Long bookId) {
        Optional<Book> bookOpt = bookDao.getById(bookId);
        if (bookOpt.isPresent())
            return bookOpt.get();
        else
            throw new BookNotFoundException(bookId);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
