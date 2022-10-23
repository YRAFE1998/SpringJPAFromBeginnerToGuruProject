package com.example.demo.DAO;


import com.example.demo.model.Author;
import com.example.demo.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Qualifier("AuthorDaoImplRepositories")
public class AuthorDaoImplRepositories implements AuthorDAO{

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<Author> listAuthorByLastNameLikeQuery(String lastName) {
        return null;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public List<Author> getByName(String name) {
        return authorRepository.findAuthorByFirstNameIgnoreCase(name).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) throws NoSuchElementException {

        Author existingAuthor;
        try{
            existingAuthor = authorRepository.findById(author.getId()).get();
            existingAuthor.setFirstName(author.getFirstName());
            existingAuthor.setLastName(author.getLastName());
            return authorRepository.save(existingAuthor);
        }
        catch (NoSuchElementException e){
            throw e;
        }


    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAllAuthorsNamedQuery() {
        return null;
    }

    @Override
    public List<Author> findAuthorByNameNamedQuery(String name) {
        return null;
    }
}
