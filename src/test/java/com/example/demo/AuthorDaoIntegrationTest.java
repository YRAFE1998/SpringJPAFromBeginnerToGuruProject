package com.example.demo;


import com.example.demo.DAO.AuthorDAO;
import com.example.demo.model.Author;
import com.example.demo.repositories.AuthorRepository;
import liquibase.pro.packaged.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan({"package com.example.demo.repositories","package com.example.demo.DAO"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AuthorDaoIntegrationTest {

    @Autowired
    @Qualifier("AuthorDaoImplRepositories")
    AuthorDAO authorDAO;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void testGetAuthorById(){
        Author author = authorDAO.getById(123456L);
        assertThat(author).isNotNull();
    }


    @Test
    void testGetAuthorList(){
        List<Author> authors = authorDAO.getByName("yousef");
        assertThat(authors).isNotEmpty();
        authors.forEach(author -> {
            //System.out.println("Author First Name : " + author.getFirstName());
            assertThat(author.getLastName()).isEqualTo("Refaat");
        });
    }

    @Test
    void testSaveAuthor(){
        Author author = new Author();
        author.setFirstName("saved author for test");
        author.setLastName("last");
        Author savedAuthor = authorDAO.saveNewAuthor(author);

        System.out.println("Saved Author : " + savedAuthor.getId() + " "
                + savedAuthor.getFirstName() + " "
                + savedAuthor.getLastName());

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }


    @Test
    void testUpdateAuthor(){
        Author author = new Author();
        author.setFirstName("Updatable Author");
        author.setLastName("Last");

        Author saved = authorDAO.saveNewAuthor(author);

        saved.setFirstName("Author");

        Author updated = authorDAO.updateAuthor(saved);

        assertThat(updated.getFirstName()).isEqualTo("Author");

    }

    @Test
    void testDeleteAuthor(){

        Author author = new Author();
        author.setFirstName("Mohamed");
        author.setLastName("Abdallah");

        Author saved = authorDAO.saveNewAuthor(author);

        authorDAO.deleteAuthorById(saved.getId());

        //Author deletedAuthor = authorDAO.getByID(saved.getId());
        /*assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDAO.getByID(saved.getId());
        });*/
        assertThrows(NoSuchElementException.class, () -> { authorDAO.getById(saved.getId());});

    }

    @Test
    void testGetAuthorByNameLike (){
        Author author = new Author();
        author.setLastName("Test Last name yyyy");
        author.setFirstName("first name");

        Author author1 = new Author();
        author1.setLastName("Test Last name xxxx");
        author1.setFirstName("first name");

        authorDAO.saveNewAuthor(author);
        authorDAO.saveNewAuthor(author1);

        List<Author> authors = authorDAO.listAuthorByLastNameLikeQuery("Test Last name");

        assertThat(authors.toArray().length).isGreaterThan(1);
        assertThat(authors.get(0).getLastName()).contains("Test Last name");

        authors.forEach((authorToDelete) ->{
            authorDAO.deleteAuthorById(authorToDelete.getId());
        });
    }

    @Test
    void testFindAllAuthors (){
        List<Author> authors = authorDAO.findAllAuthorsNamedQuery();
        assertThat(authors.toArray().length).isGreaterThan(2);
    }

    @Test
    void testFindByNameNamedQueries(){

        Author author = new Author();
        author.setFirstName("Yousef Refaat Ahmed");
        author.setLastName("Fouad Elmasry");

        authorDAO.saveNewAuthor(author);

        List<Author> authors = authorDAO.findAuthorByNameNamedQuery("Yousef Refaat Ahmed");
        assertThat(authors).isNotEmpty();
        authors.forEach(authorToAssert -> {
            //System.out.println("Author First Name : " + author.getFirstName());
            assertThat(authorToAssert.getFirstName()).isEqualTo("Yousef Refaat Ahmed");
        });

        authors.forEach((authorToDelete) -> {
            authorDAO.deleteAuthorById(authorToDelete.getId());

        });
    }



}
