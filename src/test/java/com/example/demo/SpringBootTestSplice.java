package com.example.demo;


import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = {"com.example.demo.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootTestSplice {

    @Autowired
    BookRepository bookRepository;

    /*@Rollback(value = false)
    @Order(1)
    @Test
    void testJPATestSplice(){
        long count = bookRepository.count();
        assertThat(count).isEqualTo(2);
        bookRepository.save(new Book("Title 3 ","ISBN ","Self", 123456L));
        long countAfter = bookRepository.count();
        assertThat(count).isLessThan(countAfter);
    }*/

    @Order(2)
    @Test
    void testJPATestTransaction(){
        long count = bookRepository.count();
        assertThat(count).isEqualTo(3);
        /*bookRepository.save(new Book("Title 3 ","ISBN ","Self"));
        long countAfter = bookRepository.count();
        assertThat(count).isLessThan(countAfter);*/
    }

}
