package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author getOrSaveByName(String name) {
        Optional<Author> authorOpt = authorDao.getByName(name);
        return authorOpt.orElseGet(() -> authorDao.insert(new Author(null, name)));
    }
}
