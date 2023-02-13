package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookJpaImpl implements BookJpa {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book Book) {
        if (Book.getId() == null || Book.getId() <= 0) {
            em.persist(Book);
            return Book;
        } else {
            return em.merge(Book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Optional<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
