package com.example.demo;


import com.example.demo.DAO.AuthorDAO;
import com.example.demo.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan("package com.example.demo.DAO")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AuthorDaoIntegrationTest {

    @Autowired
    @Qualifier("JDBCTemplate")
    AuthorDAO authorDAO;


    @Test
    void testGetAuthorById(){
        Author author = authorDAO.getByID(123456L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor(){
        ArrayList<Author> authors = (ArrayList<Author>) authorDAO.getByName("Yousef");
        assertThat(authors).isNotEmpty();
        authors.stream().forEach(author -> {
            //System.out.println("Author First Name : " + author.getFirstName());
            assertThat(author.getLastName()).isEqualTo("Refaat");
        });
    }

    @Test
    void testSaveAuthor(){
        Author author = new Author();
        author.setFirstName("John2");
        author.setLastName("Doe2");
        Author savedAuthor = authorDAO.saveNewAuthor(author);

        System.out.println("Saved Author : " + savedAuthor.getId() + " "
                + savedAuthor.getFirstName() + " "
                + savedAuthor.getLastName());

        assertThat(savedAuthor).isNotNull();
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
        assertThat(authorDAO.getByID(saved.getId())).isNull();
    }
}
