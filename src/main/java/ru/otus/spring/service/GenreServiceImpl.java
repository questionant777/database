package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.GenreJpa;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreJpa genreJpa;

    public GenreServiceImpl(GenreJpa genreJpa) {
        this.genreJpa = genreJpa;
    }

    @Override
    public Genre getOrSaveByName(String name) {
        Optional<Genre> genreOpt = genreJpa.findByName(name);
        return genreOpt.orElseGet(() -> genreJpa.save(Genre.builder().name(name).build()));
    }
}
