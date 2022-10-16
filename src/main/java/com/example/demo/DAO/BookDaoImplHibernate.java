package com.example.demo.DAO;

import com.example.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Qualifier("BookDaoImplementationHibernate")
public class BookDaoImplHibernate implements BookDAO{

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public Book findByIsbnTypedQuery(String isbn) {
        EntityManager entityManager = getEntityManager();
        try{
            TypedQuery<Book> typedQuery = entityManager.createQuery("" +
                    "SELECT b FROM Book b WHERE isbn = :isbn", Book.class);
            typedQuery.setParameter("isbn", isbn);
            Book book = typedQuery.getSingleResult();
            return book;
        }
        finally {
            entityManager.close();
        }

    }

    @Override
    public Book getById(Long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public List<Book> getByTitle(String title) {
        TypedQuery<Book> typedQuery = getEntityManager().createQuery("SELECT b FROM Book b WHERE" +
                " b.title = :title", Book.class);
        typedQuery.setParameter("title", title);
        return typedQuery.getResultList();
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Book.class, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
