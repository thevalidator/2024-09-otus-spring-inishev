package ru.otus.hw.services;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис для работы с комментариями")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Import({
        CommentServiceImpl.class,
        CommentConverter.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CommentConverter commentConverter;

    @DisplayName("должен найти все комментарии книги")
    @Test
    void shouldFindAllCommentsByBookId() {
        var bookId = 1L;
        var expectedComments = List.of(
                new Comment(1, "Comment-1_book-1", new Book(bookId, null, null, null)),
                new Comment(2, "Comment-2_book-1", new Book(bookId, null, null, null)));
        var r = commentService.findAllCommentsByBookId(bookId);
        assertThat(r).hasSameElementsAs(expectedComments);
        r.forEach(c -> System.out.println(commentConverter.commentToString(c)));
    }

    @DisplayName("должен найти комментарий по id")
    @Test
    void shouldGetCommentById() {
        var expectedComment = new Comment(3,
                "Comment-1_book-3",
                new Book(3L, null, null, null));
        var r = commentService.getCommentById(3L);
        assertThat(r.isPresent()).isTrue();
        assertThat(r.get()).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);
        System.out.println(commentConverter.commentToString(r.get()));
    }

    @DisplayName("должен выбросить исключение при обращении к названию книги из комментария")
    @Test
    void shouldThrowLazyInitializationExceptionWhenGetBookTitleFromFoundCommentById() {
        var foundComment = commentService.getCommentById(3L);
        assertThat(foundComment.isPresent()).isTrue();
        assertThrows(LazyInitializationException.class, () -> foundComment.get().getBook().getTitle());
    }

}