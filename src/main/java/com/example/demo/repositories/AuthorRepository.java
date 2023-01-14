package com.example.demo.repositories;

import com.example.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<List<Author>> findAuthorByFirstNameIgnoreCase(String firstName);

}
