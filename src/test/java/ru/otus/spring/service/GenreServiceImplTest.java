package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GenreServiceImplTest {

    public static final long NEW_GENRE_ID = 1L;
    public static final String NEW_GENRE_NAME = "new genre name";
    public static final long EXISTING_GENRE_ID = 5L;
    public static final String EXISTING_GENRE_NAME = "existing genre name";

    @Mock
    GenreDao genreDao;

    @InjectMocks
    GenreServiceImpl service;

    @Test
    void getOrSaveByNameForExistingGenreTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);

        when(genreDao.getByName(EXISTING_GENRE_NAME))
                .thenReturn(Optional.of(expectedGenre));

        Genre actualGenre = service.getOrSaveByName(EXISTING_GENRE_NAME);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void getOrSaveByNameSaveAndRetNotExistingGenreTest() {
        Genre newGenre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);

        when(genreDao.insert(any()))
                .thenReturn(newGenre);

        Genre actualGenre = service.getOrSaveByName(NEW_GENRE_NAME);

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(newGenre);
    }
}