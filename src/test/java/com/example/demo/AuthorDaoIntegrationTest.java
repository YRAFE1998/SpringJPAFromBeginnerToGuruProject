package com.example.demo;


import com.example.demo.DAO.AuthorDAO;
import com.example.demo.model.Author;
import com.example.demo.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;;

//@ActiveProfiles("h2")
@DataJpaTest
@ComponentScan({"package com.example.demo.DAO"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDAO authorDAO;


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


    @Transactional
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

    @Transactional
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
        //assertThrows(EmptyResultDataAccessException.class, () -> { authorDAO.getById(saved.getId());});
        assertNull(authorDAO.getById(saved.getId()));

    }
/*    @Test
    void testGetAllBooksPagedOnePageable(){
        assertThat(authorDAO.getAuthorByLastName("ElMasry",PageRequest.of(1,10))
                .toArray().length).isEqualTo(10);
    }


    @Test
    void testGetAllBooksPagedLastPageable(){
        assertThat(authorDAO.getAuthorByLastName("ElMasry",PageRequest.of(3,10))
                .toArray().length).isEqualTo(1);
    }


    @Test
    void testGetAllBooksPagedPageable(){
        assertThat(authorDAO.getAuthorByLastName("ElMasry",PageRequest.of(0,10)).toArray().length).isEqualTo(10);
    }
*/

@Test
void testGetAllBooksPagedLastPageable(){
    List<Author> authors = authorDAO.findAuthorByLastNamePageable("ElMasry",PageRequest.of(0,10,Sort.by(Sort.Order.asc("firstName"))));
    assertThat(authors.toArray().length).isEqualTo(10);
    assertThat(authors.get(0).getFirstName()).isEqualTo("Abdelaziz");
}

}
