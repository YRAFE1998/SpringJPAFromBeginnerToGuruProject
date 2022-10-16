package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface AuthorDAO {
    List<Author> listAuthorByLastNameLikeQuery(String lastName);
    Author getByID(Long id);
    List<Author> getByName(String name);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
    List<Author> findAllAuthorsNamedQuery();
    List<Author> findAuthorByNameNamedQuery(String name);
}
