package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return "Book author: %s, Id: %d, Message: %s".formatted(comment.getBook().getAuthor(), comment.getId(), comment.getMessage());
//        return "Id: %d, Message: %s".formatted(comment.getId(), comment.getMessage());
    }

}
