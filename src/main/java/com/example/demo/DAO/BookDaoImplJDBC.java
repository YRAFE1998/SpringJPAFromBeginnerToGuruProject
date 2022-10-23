package com.example.demo.DAO;

import com.example.demo.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImplJDBC implements BookDAO{

    private final JdbcTemplate jdbcTemplate;


    public BookDaoImplJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book findByIsbnTypedQuery(String isbn) {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?",getRowMapper() ,id);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title,isbn,publisher) values (?,?,?)"
        , book.getTitle(),book.getISBN(),book.getPublisher());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return getById(createdId);

    }

    @Override
    public List<Book> getByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM book WHERE title = ?", getRowMapper(), title);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, isbn = ?, publisher = ? WHERE id = ?",
                book.getTitle(),
                book.getISBN(),
                book.getPublisher(),
                book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);

    }

    @Override
    public List<Book> getBookswithISBNGreaterThan(String s) {
        return null;
    }

    private BookMapper getRowMapper() {

        return new BookMapper();
    }

}
