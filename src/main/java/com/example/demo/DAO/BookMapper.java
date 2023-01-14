package com.example.demo.DAO;

import com.example.demo.model.Book;
import liquibase.pro.packaged.B;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        String isbn = rs.getString("isbn");
        String publisher = rs.getString("publisher");
        Book book = new Book();
        book.setId(id);
        book.setPublisher(publisher);
        book.setISBN(isbn);
        book.setTitle(title);
        return book;
    }
}
