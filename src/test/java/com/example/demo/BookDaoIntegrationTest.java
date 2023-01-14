package com.example.demo;
import com.example.demo.DAO.BookDAO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan("package com.example.demo.DAO")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDAO bookDAO;


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

        assertThrows(EmptyResultDataAccessException.class, () -> {bookDAO.getById(savedBook.getId());});

    }

    /*@Test
    void testGetAllBooksPagedLast(){
        assertThat(bookDAO.getAllBooksByPage(10,10).toArray().length).isEqualTo(4);
    }

    @Test
    void testGetAllBooksPaged(){
        assertThat(bookDAO.getAllBooksByPage(10,0).toArray().length).isEqualTo(10);
    }


    @Test
    void testGetAllBooksPagedLastPageable(){
        assertThat(bookDAO.getAllBookByPageable(PageRequest.of(1,10)).toArray().length).isEqualTo(4);
    }

    @Test
    void testGetAllBooksPagedPageable(){
        assertThat(bookDAO.getAllBookByPageable(PageRequest.of(0,10)).toArray().length).isEqualTo(10);
    }*/

    @Test
    void testGetAllBooksPagedPageable(){
        List<Book> books = bookDAO.findAllBooksPageable(PageRequest.of(0,10));
        assertThat(books.toArray().length).isEqualTo(10);
        assertThat(books.get(0).getTitle()).isEqualTo("Abc");
    }
    @Test
    void testGetAllBooksPagedLastPageable(){
        assertThat(bookDAO.findAllBooksPageable(PageRequest.of(2,10)).toArray().length).isEqualTo(7);
    }
    @Test
    void testFindByTitlePageableSpringDataJpa(){
        List<Book> books = bookDAO.findByTitle("Test Title", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("publisher"))));
        assertThat(books.toArray().length).isEqualTo(3);
        assertThat(books.get(0).getPublisher()).isEqualTo("Mazen Elmasry");
    }


}
