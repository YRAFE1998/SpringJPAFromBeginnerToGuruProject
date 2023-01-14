package com.example.demo.bootstrap;

import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Profile({"local","default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public DataInitializer(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*bookRepository.deleteAll();
        Book book1 = new Book("Math101","123","Yousef Refaat",123456L);
        Book savedBook = bookRepository.save(book1);

        Book book2 = new Book("Math102","1234","Yousef Refaat",123457L);
        Book savedBook2 = bookRepository.save(book2);

        AuthorUUID authorUUID = new AuthorUUID();

        authorUUID.setFirstName("Yousef");
        authorUUID.setLastName("Refaat");
        AuthorUUID savedAuthor = authorRepository.save(authorUUID);
        System.out.println("Saved Author UUID : " + savedAuthor.getId());

        BookUuid bookUuid = new BookUuid();

        bookUuid.setISBN("ISBNNO");
        bookUuid.setPublisher("Publisher publisher");
        bookUuid.setTitle("Book Title");
        //bookUuid.setAuthorId(UUID.fromString("6749075c-258d-4ec0-8ba6-d8284d98c0ce"));

        BookUuid savedBookUUID = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUUID.getId());
        */

    }
}
