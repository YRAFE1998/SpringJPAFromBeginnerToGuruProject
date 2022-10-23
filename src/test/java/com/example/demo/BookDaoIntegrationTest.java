package com.example.demo;
import com.example.demo.DAO.AuthorDAO;
import com.example.demo.DAO.BookDAO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan("package com.example.demo.DAO")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    @Qualifier("BookDaoImplRepositories")
    BookDAO bookDAO;

    @Autowired
    BookRepository bookRepository;


    @Test
    void testSaveBook(){
        Book book = new Book();

        Author author = new Author();

        book.setISBN("isbn34");
        book.setPublisher("Publisher34");
        book.setTitle("title for test Save book");

        /*author.setFirstName("Book34 author");
        author.setLastName("last");
        Author saved = authorDAO.saveNewAuthor(author);

        book.setAuthor(saved);
        */
        Book savedBook = bookDAO.saveNewBook(book);
        System.out.println(savedBook.getAuthor() + ", " + savedBook.getTitle() + ", " + savedBook.getId());
        assertThat(savedBook).isNotNull();
        //assertThat(savedBook.getTitle()).isEqualTo("title for test Save book");
    }

    @Test
    void testGetById(){
        Book book = new Book();
        book.setISBN("ISBN Test");
        book.setTitle("Test Title");
        book.setPublisher("TestPublisher");

        Book savedBook = bookDAO.saveNewBook(book);

        Book retrievedBook = bookDAO.getById(savedBook.getId());
        assertThat(retrievedBook).isNotNull();

    }

    @Test
    void testGetByTitle(){
        Book book = new Book();
        book.setISBN("ISBN Test");
        book.setTitle("Test Title for GetBy title");
        book.setPublisher("Test Publisher");

        Book savedBook = bookDAO.saveNewBook(book);
        Book retrievedBook = bookDAO.getByTitle(savedBook.getTitle()).get(0);
        assertThat(retrievedBook).isNotNull();


    }

    @Test
    void testUpdatBook(){
        Book book = new Book();
        book.setISBN("ISBN Test");
        book.setTitle("Test Title for GetBy title");
        book.setPublisher("Test Publisher");

        Book savedBook = bookDAO.saveNewBook(book);
        System.out.println("Saved Book : " + savedBook.getTitle() + ", " + savedBook.getAuthor() + ", " +  savedBook.getPublisher() + ", " + savedBook.getId());
        savedBook.setTitle("Test Title for GetBy Title after update");

        Book updatedBook = bookDAO.updateBook(savedBook);

        assertThat(updatedBook.getTitle()).isEqualTo("Test Title for GetBy Title after update");
    }

    @Transactional
    @Test
    void testDeleteBook(){
        Book book = new Book();
        book.setISBN("ISBN Test for delete");
        book.setTitle("Test Title for Delete");
        book.setPublisher("Test Publisher for Delete");

        Book savedBook = bookDAO.saveNewBook(book);

        bookDAO.deleteBookById(savedBook.getId());

        assertThrows(NoSuchElementException.class, () -> {bookDAO.getById(savedBook.getId());});

    }

    @Transactional
    @Test
    void testfindByISBN(){
        Book book = new Book();
        book.setISBN("Isbn for testbyISBN");
        book.setPublisher("publisher");
        book.setTitle("tytool");
        bookDAO.saveNewBook(book);
        Book returnedBook = bookDAO.findByIsbnTypedQuery(book.getISBN());

        assertThat(returnedBook).isNotNull();
        assertThat(returnedBook.getISBN()).isEqualTo("Isbn for testbyISBN");

        bookDAO.deleteBookById(returnedBook.getId());

    }

    @Test
    void testGetByTitleNullable(){
        assertNull(bookRepository.readBookByTitle("kjhb"));
    }

    @Test
    void testWroteQuery(){
        Stream<Book> books = bookRepository.getAllBooksWithAuthors();
        books.forEach(book ->assertThat(book.getISBN()).isNotNull());
    }

    @Test
    void testWroteQueryNamedParameters(){
        Stream<Book> books = bookRepository.getBookByIsbn("isbn35");
        books.forEach(book ->assertThat(book.getISBN()).isEqualTo("isbn35"));
    }


    @Test
    void testNaiveSqlQuery(){
        List<Book> books = bookDAO.getBookswithISBNGreaterThan("20");
        books.forEach(book ->assertThat(Integer.parseInt(book.getISBN().replaceAll("[^0-9]", ""))).isGreaterThan(20));
    }

    @Test
    void testNamedQueryJPA(){
        List<Book> books = bookRepository.jpaQuery("publisher x");
        books.forEach(book ->assertThat(book.getPublisher()).isEqualTo("publisher x"));
    }
}
