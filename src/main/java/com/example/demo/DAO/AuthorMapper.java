package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorMapper implements RowMapper<Author> {


    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("author.id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        try {
            if(rs.getString("isbn") != null){
                author.setBooks(new ArrayList<>());
                author.getBooks().add(mapBook(rs));
            }

            while(rs.next()){
                author.getBooks().add(mapBook(rs));
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }


        return author;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book authorBook = new Book();
        authorBook.setTitle(rs.getString("title"));
        authorBook.setPublisher(rs.getString("publisher"));
        authorBook.setId(rs.getLong("book_id"));
        authorBook.setISBN(rs.getString("isbn"));
        return authorBook;
    }

}
