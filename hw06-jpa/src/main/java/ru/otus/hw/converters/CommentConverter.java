package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return "Id: %d, message: %s, book id: %d".formatted(
                comment.getId(),
                comment.getMessage(),
                comment.getBook().getId());
    }

}
