package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("select count(*) from genre", Integer.class);
    }

    @Override
    public Genre insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into genre (`name`) values(?)", new String[] { "id" });
            ps.setString(1, genre.getName());
            return ps;
        }, keyHolder);

        return new Genre(keyHolder.getKey().longValue(), genre.getName());
    }

    @Override
    public Optional<Genre> getById(long id) {
        Genre genre = null;
        try {
            genre = namedParameterJdbcOperations.queryForObject(
                    "select * from genre where id = :id", Map.of("id", id), new GenreMapper());
            return Optional.of(genre);
        } catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(genre);
        }
    }

    @Override
    public Optional<Genre> getByName(String name) {
        Genre genre = null;
        try {
            genre = namedParameterJdbcOperations.queryForObject(
                    "select * from genre where `name` = :name", Map.of("name", name), new GenreMapper());
            return Optional.of(genre);
        } catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(genre);
        }
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select * from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "delete from genre where id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }

    }
}
