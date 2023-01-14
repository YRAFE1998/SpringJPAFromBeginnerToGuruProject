package com.example.demo.DAO;

import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Qualifier("BookDaoImplementationHibernate")
public class BookDaoImplementation implements BookDAO{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    BookRepository bookRepository;


    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
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

    @Override
    public List<Book> findByTitle(String title, Pageable pageable) {
        return bookRepository.findBookByTitle(title,pageable).get();
    }

    @Override
    public List<Book> findAllBooksPageable(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
        /*
        EntityManager em = getEntityManager();
        try {
            String hql = "SELECT b FROM Book b order by b.title " + pageable.getSort().
                    getOrderFor("title").getDirection().name();
            Query query = em.createQuery(hql,Book.class);
            //query.setParameter("limit", pageable.getPageSize());
            //query.setParameter("offset", pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            List<Book> books = query.getResultList();
            return books;
        }
        finally {
            em.close();
        }
        */
    }


}