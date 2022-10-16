package com.example.demo;
import com.example.demo.DAO.AuthorDAO;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan("package com.example.demo.DAO")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class BookDaoIntegrationTest {

    @Autowired
    BookDAO bookDAO;
    @Autowired
    @Qualifier("JDBCTemplate")
    AuthorDAO authorDAO;

    @Test
    void testSaveBook(){
        Book book = new Book();

        Author author = new Author();

        book.setISBN("isbn34");
        book.setPublisher("Publisher34");
        book.setTitle("title for test Save book");

        author.setFirstName("Book34 author");
        author.setLastName("last");
        Author saved = authorDAO.saveNewAuthor(author);

        book.setAuthor(saved);

        Book savedBook = bookDAO.saveNewBook(book);
        System.out.println(savedBook.getAuthor() + ", " + savedBook.getTitle() + ", " + savedBook.getId());
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("title for test Save book");
    }

    @Test
    void testGetById(){
        /*Book book = new Book();
        book.setISBN("ISBN Test");
        book.setTitle("Test Title");
        book.setPublisher("TestPublisher");

        Book savedBook = bookDAO.saveNewBook(book);
        */
        Book retrievedBook = bookDAO.getById(16L);
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

    @Test
    void testDeleteBook(){
        Book book = new Book();
        book.setISBN("ISBN Test");
        book.setTitle("Test Title for GetBy title");
        book.setPublisher("Test Publisher");

        Book savedBook = bookDAO.saveNewBook(book);
        bookDAO.deleteBookById(savedBook.getId());
        assertThrows(EmptyResultDataAccessException.class, ()-> {
            bookDAO.getById(savedBook.getId());

        });

    }

}
