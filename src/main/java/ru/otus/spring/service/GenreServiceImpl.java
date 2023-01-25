package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getOrSaveByName(String name) {
        Optional<Genre> genreOpt = genreDao.getByName(name);
        return genreOpt.orElseGet(() -> genreDao.insert(new Genre(null, name)));
    }
}
