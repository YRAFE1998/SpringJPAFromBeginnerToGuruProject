package com.example.demo.DAO;

import com.example.demo.model.Author;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Qualifier("JDBCTemplate")
public class AuthorDaoImplJDBCTemplate implements AuthorDAO{


    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImplJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Author> listAuthorByLastNameLikeQuery(String lastName) {
        return null;
    }

    @Override
    public Author getByID(Long id) {
        String sql = "SELECT author.id as id, first_name, last_name, book.id as book_id, book.isbn, " +
                "book.title, book.publisher from author left outer join book on author.id = book.author_id" +
                " WHERE author.id = ?";


        //return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id = ?", getRowMapper(), id);
        return jdbcTemplate.query(sql, new AuthorExtractor(), id);
    }

    @Override
    public List<Author> getByName(String name) {
        return jdbcTemplate.query("SELECT * FROM author WHERE first_name = ?", getRowMapper(), name);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO author (first_name, last_name) VALUES(?,?)",
                author.getFirstName(), author.getLastName());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getByID(createdId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?",
                author.getFirstName(),
                author.getLastName(),
                author.getId());
        return this.getByID(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", id);
    }

    @Override
    public List<Author> findAllAuthorsNamedQuery() {
        return null;
    }

    @Override
    public List<Author> findAuthorByNameNamedQuery(String name) {
        return null;
    }


    private RowMapper<Author> getRowMapper(){
        return new AuthorMapper();
    }
}
