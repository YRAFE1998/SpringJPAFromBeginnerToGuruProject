package com.example.demo.DAO;

import com.example.demo.model.Author;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface AuthorDAO {
    List<Author> listAuthorByLastNameLikeQuery(String lastName);
    Author getById(Long id);
    List<Author> getByName(String name);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author) throws NoSuchElementException;
    void deleteAuthorById(Long id);
    List<Author> findAllAuthorsNamedQuery();
    List<Author> findAuthorByNameNamedQuery(String name);
}
