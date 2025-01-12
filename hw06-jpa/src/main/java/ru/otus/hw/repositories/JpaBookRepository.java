package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    private static final String FETCH_GRAPH_PROP = "jakarta.persistence.fetchgraph";

    @PersistenceContext
    private final EntityManager em;
    private final EntityGraph<?> bookGraph;

    @Autowired
    public JpaBookRepository(EntityManager em) {
        this.em = em;
        this.bookGraph = em.getEntityGraph("book-author-genres");;
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> props = Map.of(FETCH_GRAPH_PROP, getBookEntityGraph());
        return Optional.ofNullable(em.find(Book.class, id, props));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .setHint(FETCH_GRAPH_PROP, getBookEntityGraph())
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() > 0) {
            book = em.merge(book);
        } else {
            em.persist(book);
        }
        return book;
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }

    private EntityGraph<?> getBookEntityGraph() {
        return bookGraph;
    }

}
