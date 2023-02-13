package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorJpa;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorJpa authorJpa;

    public AuthorServiceImpl(AuthorJpa authorJpa) {
        this.authorJpa = authorJpa;
    }

    @Override
    public Author getOrSaveByName(String name) {
        Optional<Author> authorOpt = authorJpa.findByName(name);
        return authorOpt.orElseGet(() -> authorJpa.save(Author.builder().name(name).build()));
    }
}
