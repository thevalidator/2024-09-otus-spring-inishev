package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        return em.createQuery("select c from Comment c where c.book.id = :id", Comment.class)
                .setParameter("id", bookId)
                .getResultList();
    }

    @Override
    public Optional<Comment> findById(long commentId) {
        return Optional.ofNullable(em.find(Comment.class, commentId));
    }

}
