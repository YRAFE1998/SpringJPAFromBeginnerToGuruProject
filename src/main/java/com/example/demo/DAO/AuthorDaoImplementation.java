package com.example.demo.DAO;

import com.example.demo.model.Author;
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
@Qualifier("AuthorDaoHibernateImplementation")
public class AuthorDaoImplementation implements AuthorDAO{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public List<Author> getByName(String name) {
        TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a " +
                "WHERE a.firstName = :first_name", Author.class);
        query.setParameter("first_name", name);

        return query.getResultList();

    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.getTransaction().commit();

    }

    @Override
    public List<Author> findAllAuthorsPageable(Pageable pageable) {
        return null;
    }

    @Override
    public List<Author> findAuthorByLastNamePageable(String lastName, Pageable pageable) {
        EntityManager em = getEntityManager();
        String hql = "SELECT a FROM Author a WHERE a.lastName = :lastName";
        if(pageable.getSort().isSorted())
            hql += " order by firstName " + pageable.getSort().getOrderFor("firstName")
                    .getDirection().name();
        try{
            Query query = em.createQuery(hql,Author.class);
            query.setParameter("lastName", lastName);
            query.setMaxResults(pageable.getPageSize());
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            return query.getResultList();
        }
        finally {
            em.close();
        }
    }

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}