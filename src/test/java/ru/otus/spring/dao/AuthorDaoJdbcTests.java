package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.*;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTests {

    private static final int EXPECTED_AUTHOR_COUNT = 1;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "Kristi";

    @Autowired
    private AuthorDao authorDao;

    @Test
    void countTest() {
        int actualPersonsCount = authorDao.count();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @Test
    void insertTest() {
        Author newAuthor = new Author(null, "Twain");
        Author savedAuthor = authorDao.insert(newAuthor);
        Author actualAuthor = authorDao.getById(savedAuthor.getId()).orElse(new Author());
        assertThat(actualAuthor).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(newAuthor);
    }

    @Test
    void getByIdExistingAuthorTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID).orElse(new Author());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void deleteByIdOkTest() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        Optional<Author> authorOpt = authorDao.getById(EXISTING_AUTHOR_ID);

        assertThat(authorOpt.isPresent()).isFalse();
    }

    @Test
    void getAllTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }

}
