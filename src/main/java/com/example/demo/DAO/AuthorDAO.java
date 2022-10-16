package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface AuthorDAO {
    Author getByID(Long id);
    List<Author> getByName(String name);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
}
