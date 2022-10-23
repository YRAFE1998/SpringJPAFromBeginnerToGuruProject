package com.example.demo.repositories;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorUUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<List<Author>> findAuthorByFirstNameIgnoreCase(String firstName);

}
