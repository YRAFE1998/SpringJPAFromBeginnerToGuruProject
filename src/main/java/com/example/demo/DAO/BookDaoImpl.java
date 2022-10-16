package com.example.demo.DAO;

import com.example.demo.model.Book;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl{ //implements BookDAO{

    private final DataSource source;

    public BookDaoImpl(DataSource source) {
        this.source = source;
    }

    //@Override
    public Book getById(Long id) {
        return null;
    }

    //@Override
    public Book saveNewBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("INSERT INTO book (isbn, publisher, title, author_id) values(?,?,?,?)");
            ps.setString(1,book.getISBN());
            ps.setString(2,book.getPublisher());
            ps.setString(3, book.getTitle());
            if(book.getAuthor() != null)
                ps.setLong(4,book.getAuthor().getId());
            ps.execute();

            Statement statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if(resultSet.next()){
                Long savedId = resultSet.getLong(1);
                Book retrievedBook = getById(savedId);
                return(retrievedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeAll(connection,ps,resultSet);

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
}
