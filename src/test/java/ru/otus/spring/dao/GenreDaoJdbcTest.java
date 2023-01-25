package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRE_COUNT = 1;
    private static final Long EXISTING_GENRE_ID = 2L;
    private static final String EXISTING_GENRE_NAME = "detective";

    @Autowired
    private GenreDao genreDao;

    @Test
    void countTest() {
        int actualPersonsCount = genreDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    void insertTest() {
        Genre newGenre = new Genre(null, "poema");
        Genre savedGenre = genreDao.insert(newGenre);
        Genre actualGenre = genreDao.getById(savedGenre.getId()).orElse(new Genre());
        assertThat(actualGenre).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newGenre);
    }

    @Test
    void getByIdExistinggenreTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getById(EXISTING_GENRE_ID).orElse(new Genre());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void deleteByIdOkTest() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_GENRE_ID);

        Optional<Genre> genreOpt = genreDao.getById(EXISTING_GENRE_ID);

        assertThat(genreOpt.isPresent()).isFalse();
    }

    @Test
    void getAllTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }
}