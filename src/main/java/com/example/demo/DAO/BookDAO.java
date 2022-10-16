package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;

import java.util.List;

public interface BookDAO {
    Book getById(Long id);
    Book saveNewBook(Book book);
    List<Book> getByTitle(String title);
    Book updateBook(Book book);
    void deleteBookById(Long id);
}
