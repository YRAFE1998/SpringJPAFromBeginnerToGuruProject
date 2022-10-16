package com.example.demo.DAO;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("DaoJDBC")
public class AuthorDaoImplDaoJDBC implements AuthorDAO{


    private final DataSource source;

    public AuthorDaoImplDaoJDBC(DataSource source) {
        this.source = source;
    }

    private Author getAuthorFromRs(ResultSet rs) {
        Author author = new Author();
        try {
            author.setId(rs.getLong("id"));
            author.setFirstName(rs.getString("first_name"));
            author.setLastName(rs.getString("last_name"));
            return author;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void closeAll(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(ps != null){
                ps.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLikeQuery(String lastName) {
        return null;
    }

    @Override
    public Author getByID(Long id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM  author WHERE id = ?");
            ps.setLong(1,id);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                return(getAuthorFromRs(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,resultSet);

        }
        return null;
    }

    @Override
    public List<Author> getByName(String name) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM  author WHERE first_name = ?");
            ps.setString(1,name);
            resultSet = ps.executeQuery();
            List<Author> returnedAuthors = new ArrayList<Author>();
            while(resultSet.next()){
                Author author = getAuthorFromRs(resultSet);
                returnedAuthors.add(author);
            }
            return returnedAuthors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,resultSet);
        }
        return new ArrayList<>();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("INSERT INTO author (first_name, last_name) values(?,?)");
            ps.setString(1,author.getFirstName());
            ps.setString(2,author.getLastName());
            ps.execute();

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if(resultSet.next()){
                Long savedId = resultSet.getLong(1);
                System.out.println("Last ID = "+ resultSet.getLong(1));
                Author retrievedAuthor = getByID(savedId);
                return(retrievedAuthor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,resultSet);

        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        if(author.getId() == null)
            return null;
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE author set first_name = ?, last_name= ? WHERE id = ?");
            ps.setString(1,author.getFirstName());
            ps.setString(2,author.getLastName());
            ps.setLong(3,author.getId());
            ps.execute();
            //Statement statement = connection.createStatement();

            //resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            /*if(resultSet.next()){
                Long savedId = resultSet.getLong(1);
                System.out.println("Last ID = "+ resultSet.getLong(1));
                Author retrievedAuthor = getByID(savedId);
                return(retrievedAuthor);
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,null);

        }
        return(getByID(author.getId()));
    }

    @Override
    public void deleteAuthorById(Long id){
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("DELETE FROM author WHERE id = ?");
            ps.setLong(1,id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,null);
        }
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
