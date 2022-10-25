package com.example.comics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComicBookService {

    private List<ComicBook> comicBooks;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ComicBookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        loadComicBooks();
        System.out.println("using constructor...");
    }

    @PostConstruct
    public void loadComicBooks() {
        System.out.println("ComicBookService() called...");
        comicBooks = new ArrayList<>();
        comicBooks.add(new ComicBook(1,"Hawkeye vs. Deadpool","Gerry Duggan","Marvel Comics","September 2014"));
        comicBooks.add(new ComicBook(2,"Guardians of the Galaxy: The Final Gauntlet", "Donny Cates","Marvel Comics","January 2019"));
        comicBooks.add(new ComicBook(3,"Annihilation","Keith Giffen","Marvel Comics","May 2014"));
    }

    public List<ComicBook> list() {
        String sql = "SELECT * FROM COMICBOOK";
        return jdbcTemplate.query(sql,new ComicBookRowMapper());
    }

    public ComicBook get(int id) {
        String sql = "SELECT * FROM COMICBOOK WHERE ID = ?";
        ComicBook comicBook = jdbcTemplate.queryForObject(sql, new Object[]{id}, new ComicBookRowMapper());
        return comicBook;
    }

    public void create(ComicBook comicBook) {
        String sql = "INSERT INTO COMICBOOK (id,title, author, publisher, releaseDate)"
                + " VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, comicBook.getId(), comicBook.getTitle(), comicBook.getAuthor(),
                comicBook.getPublisher(), comicBook.getReleaseDate());
    }

    public void update(ComicBook comicBook, int id) {
        String sql = "UPDATE COMICBOOK SET title=?, author=?, publisher=?, "
                + "releaseDate=? WHERE id=?";
        jdbcTemplate.update(sql, comicBook.getTitle(), comicBook.getAuthor(),
                comicBook.getPublisher(), comicBook.getReleaseDate(),id);
    }

    public boolean exists(ComicBook comicBook) {
        ComicBook found = comicBooks.stream().filter(b -> b.getTitle().equalsIgnoreCase(comicBook.getTitle())).findFirst().orElse(null);
        return found != null;
    }

    public void delete(int id){
        String sql = "DELETE FROM COMICBOOK WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}

