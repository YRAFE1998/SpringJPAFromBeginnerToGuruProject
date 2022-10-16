package com.example.demo;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackages = {"com.example.demo.bootstrap"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MigrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testJPATestSplice(){
        long count = bookRepository.count();
        assertThat(count).isEqualTo(2);
    }

}
