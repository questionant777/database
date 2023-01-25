package ru.otus.spring.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public Book insert(Book book) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcOperations.getJdbcOperations().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into book (id, `name`, authorid, genreid) values (default, ?, ?, ?)",
                    new String[] { "id" });
            ps.setString(1, book.getName());
            ps.setLong(2, book.getAuthorId());
            ps.setLong(3, book.getGenreId());
            return ps;
        }, keyHolder);

        return new Book(
                (Long) keyHolder.getKey().longValue(),
                book.getName(),
                book.getAuthorId(),
                book.getGenreId()
        );
    }

    @Override
    public Optional<Book> getById(long id) {
        Book book = null;
        try {
            book = namedParameterJdbcOperations.queryForObject(
                    "select * from book where id = :id", Map.of("id", id), new BookMapper());
            return Optional.of(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.ofNullable(book);
        }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select * from book", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(
                    (Long)resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getLong("authorid"),
                    resultSet.getLong("genreid")
            );
        }

    }
}
