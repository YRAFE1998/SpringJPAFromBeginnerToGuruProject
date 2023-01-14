package com.example.demo.repositories;

import com.example.demo.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<List<Book>> findBookByTitleIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE b.ISBN is NOT NULL")
    Stream<Book> getAllBooksWithAuthors();


    @Nullable
    Book readBookByTitle(@Nullable String title);

    @Query("SELECT b FROM Book b WHERE b.ISBN = :isbn")
    Stream<Book> getBookByIsbn(@Param("isbn") String isbn);

    @Query(value = "SELECT * FROM book", nativeQuery = true)
    Stream<Book> getAllBooks();

    List<Book> jpaQuery(@Param("publisher") String publisher);

    Optional<List<Book>> findBookByTitle(String title, Pageable pageable);
}
