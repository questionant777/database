package ru.otus.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentJpaImpl implements BookCommentJpa{

    @PersistenceContext
    private EntityManager em;

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null || bookComment.getId() <= 0) {
            em.persist(bookComment);
            return bookComment;
        } else {
            return em.merge(bookComment);
        }
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findByBookId(long bookid) {
        TypedQuery<BookComment> query = em.createQuery("select c " +
                        "from BookComment c " +
                        "where c.book.id = :bookid",
                BookComment.class);
        query.setParameter("bookid", bookid);
        return query.getResultList();
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select a from BookComment a", BookComment.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from BookComment a " +
                "where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
