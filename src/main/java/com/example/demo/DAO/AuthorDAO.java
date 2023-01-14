package com.example.demo.DAO;

import com.example.demo.model.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;

public interface AuthorDAO {
    Author getById(Long id);
    List<Author> getByName(String name);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author) throws NoSuchElementException;
    void deleteAuthorById(Long id);
    List<Author> findAllAuthorsPageable(Pageable pageable);
    List<Author> findAuthorByLastNamePageable(String lastName, Pageable pageable);
}
