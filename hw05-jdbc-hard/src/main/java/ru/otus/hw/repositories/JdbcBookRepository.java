package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        var book = getBookById(id);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);
        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresentOrElse(book -> {
            removeGenresRelationsFor(book);
            jdbc.update("delete from books where id = :id", Map.of("id", id));
        }, () -> {
            throw new EntityNotFoundException("Book with id=" + id + " not found");
        });
    }

    private List<Book> getAllBooksWithoutGenres() {
        return jdbc.query("select b.id, b.title, a.id as author_id, a.full_name as author_full_name " +
                        "from books b inner join authors a on b.author_id = a.id",
                new BookRowMapper());
    }

    private List<BookGenreRelation> getGenreRelationsByBookId(long id) {
        return jdbc.query("select book_id, genre_id from books_genres where book_id = :book_id",
                Map.of("book_id", id),
                new BookGenreRelationRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("select book_id, genre_id from books_genres", new BookGenreRelationRowMapper());
    }

    private Book getBookById(long id) {
        var relations = getGenreRelationsByBookId(id);
        var genreIds = relations.stream().map(r -> r.genreId).collect(Collectors.toSet());
        var genres = genreRepository.findAllByIds(genreIds);
        return jdbc.query("select b.id, b.title, a.id as author_id, a.full_name as author_full_name " +
                        "from books b " +
                        "inner join authors a on b.author_id = a.id " +
                        "where b.id = :id",
                Map.of("id", id),
                new BookResultSetExtractor(genres));
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {

        var b = booksWithoutGenres.stream().collect(Collectors.toMap(Book::getId, Function.identity()));
        var g = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
        for (BookGenreRelation relation: relations) {
            long bookId = relation.bookId;
            long genreId = relation.genreId;
            Book book = b.get(bookId);
            Genre genre = g.get(genreId);
            book.getGenres().add(genre);
        }

    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());
        jdbc.update("insert into books(title, author_id) values (:title, :author_id)",
                params,
                keyHolder,
                new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);
        return book;
    }

    private Book update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("author_id", book.getAuthor().getId());
        int rowsUpdated = jdbc.update("update books set title = :title, author_id = :author_id where id = :id", params);
        if (rowsUpdated < 1) {
            throw new EntityNotFoundException("Book with id=" + book.getId() + " not found");
        }
        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        book = getBookById(book.getId());
        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        List<MapSqlParameterSource> params = new ArrayList<>();
        for (Genre genre: book.getGenres()) {
            var param = new MapSqlParameterSource()
                    .addValue("book_id", book.getId())
                    .addValue("genre_id", genre.getId());
            params.add(param);
        }
        jdbc.batchUpdate("insert into books_genres(book_id, genre_id) values (:book_id, :genre_id)",
                params.toArray(new MapSqlParameterSource[0]));
    }

    private void removeGenresRelationsFor(Book book) {
        var params = new MapSqlParameterSource();
        params.addValue("book_id", book.getId());
        jdbc.update("delete from books_genres where book_id = :book_id", params);
    }

    private static class BookGenreRelationRowMapper implements RowMapper<BookGenreRelation> {

        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            long bookId = rs.getLong("book_id");
            long genreId = rs.getLong("genre_id");
            return new BookGenreRelation(bookId, genreId);
        }

    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long authorId = rs.getLong("author_id");
            String authorFullName = rs.getString("author_full_name");

            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setAuthor(new Author(authorId, authorFullName));
            book.setGenres(new ArrayList<>());

            return book;
        }

    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        private final List<Genre> genres;

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            Book book = null;
            if (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                long authorId = rs.getLong("author_id");
                String authorFullName = rs.getString("author_full_name");
                book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setAuthor(new Author(authorId, authorFullName));
                book.setGenres(genres);
            }
            return book;
        }

    }

    private record BookGenreRelation(long bookId, long genreId) {

    }

}
