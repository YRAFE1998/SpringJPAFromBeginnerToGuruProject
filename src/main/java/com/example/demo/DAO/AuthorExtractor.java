package com.example.demo.DAO;

import com.example.demo.model.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<Author> {
    @Override
    public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
        if(rs.next()){
            return new AuthorMapper().mapRow(rs,0);
        }
        return null;

    }
}
