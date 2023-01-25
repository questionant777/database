package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("select count(*) from author", Integer.class);
    }

    @Override
    public Author insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into author (`name`) values(?)", new String[] { "id" });
            ps.setString(1, author.getName());
            return ps;
        }, keyHolder);

        return new Author(keyHolder.getKey().longValue(), author.getName());
    }

    @Override
    public Optional<Author> getById(long id) {
        Author author = null;
        try {
            author = namedParameterJdbcOperations.queryForObject(
                    "select * from author where id = :id", Map.of("id", id), new AuthorMapper());
            return Optional.of(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(author);
        }
    }

    @Override
    public Optional<Author> getByName(String name) {
        Author author = null;
        try {
            author = namedParameterJdbcOperations.queryForObject(
                    "select * from author where `name` = :name", Map.of("name", name), new AuthorMapper());
            return Optional.of(author);
        } catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(author);
        }
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select * from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "delete from author where id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }

    }
}
