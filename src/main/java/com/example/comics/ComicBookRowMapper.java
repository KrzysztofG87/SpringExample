package com.example.comics;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComicBookRowMapper implements RowMapper<ComicBook> {
    @Override
    public ComicBook mapRow(ResultSet rs, int rowNum) throws SQLException {
        ComicBook comicBook = new ComicBook(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
        return comicBook;
    }
}
