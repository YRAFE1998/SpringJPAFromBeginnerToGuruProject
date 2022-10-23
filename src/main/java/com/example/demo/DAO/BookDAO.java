package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

import java.util.List;
import java.util.stream.Stream;

public interface BookDAO {
    Book findByIsbnTypedQuery(String isbn);
    Book getById(Long id);
    Book saveNewBook(Book book);
    List<Book> getByTitle(String title);
    Book updateBook(Book book);
    void deleteBookById(Long id);

    List<Book> getBookswithISBNGreaterThan(String s);
}
