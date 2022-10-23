package com.example.demo.DAO;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Component
@Qualifier("BookDaoImplRepositories")
public class BookDaoImplRepositories implements BookDAO{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book findByIsbnTypedQuery(String isbn) {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getByTitle(String title) {
        return bookRepository.findBookByTitleIgnoreCase(title).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        Book existingBook = bookRepository.findById(book.getId()).orElseThrow(NoSuchElementException::new);
        existingBook.setTitle(book.getTitle());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setTitle(book.getTitle());
        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getBookswithISBNGreaterThan(String s) {
        Stream<Book> books =  bookRepository.getAllBooks();
        List<Book> returnedBooks = new ArrayList<>();
        books.forEach(book -> {
            if(Integer.parseInt(book.getISBN().replaceAll("[^0-9]]","")) > Integer.parseInt(s))
                returnedBooks.add(book);
        });
        return returnedBooks;
    }
}
