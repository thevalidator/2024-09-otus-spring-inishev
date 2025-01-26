package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями ")
@DataJpaTest
@Import(JpaCommentRepository.class)
class JpaCommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private JpaCommentRepository jpaCommentRepository;

    @DisplayName("должен загружать все комментарии по id книги")
    @ParameterizedTest
    @MethodSource("getBookIds")
    void shouldFindAllCommentsByBookId(long bookId) {
        var expectedComments = em.getEntityManager()
                .createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
        var foundComments = jpaCommentRepository.findAllByBookId(bookId);
        assertThat(foundComments).usingRecursiveComparison().isEqualTo(expectedComments);
        System.out.println(foundComments);
    }

    @DisplayName("должен загружать комментарий по id")
    @ParameterizedTest
    @MethodSource("getCommentIds")
    void shouldFindCommentById(long commentId) {
        var expectedComment = em.find(Comment.class, commentId);
        var foundComment = jpaCommentRepository.findById(expectedComment.getId()).orElse(null);

        assertThat(foundComment).isNotNull()
                .matches(c -> c.getId() == expectedComment.getId())
                .matches(c -> c.getId() == commentId)
                .matches(c -> c.getMessage().equals(expectedComment.getMessage()))
                .matches(c -> c.getBook().getId() == expectedComment.getBook().getId());
        System.out.println(foundComment);
    }

    public static List<Long> getCommentIds() {
        return LongStream.range(1, 4).boxed().toList();
    }

    public static List<Long> getBookIds() {
        return LongStream.range(1, 4).boxed().toList();
    }

}