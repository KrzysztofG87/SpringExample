package com.example.comics;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comicBooks")
public class ComicBookController {

        private final ComicBookService comicBookService;

        public ComicBookController(ComicBookService comicBookService) {
            System.out.println("BookController() called...");
            this.comicBookService = comicBookService;
            System.out.println(comicBookService.getClass().getName());
        }

        @GetMapping
        public List<ComicBook> list() {
            return comicBookService.list();
        }

        @GetMapping("/{id}")
        public ComicBook get(@PathVariable int id) throws ComicBookNotFoundException {
                ComicBook comicBook;
            try{
                comicBook = comicBookService.get(id);
            }catch(EmptyResultDataAccessException e){
                throw new ComicBookNotFoundException(id);
            }
            return comicBook;
        }

        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping
        public ComicBook create(@RequestBody ComicBook comicBook) throws ComicBookAlreadyExistsException {
            if(comicBookService.exists(comicBook)) {
                throw new ComicBookAlreadyExistsException(comicBook);
            } else {
                comicBookService.create(comicBook);
                return comicBook;
            }
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @PutMapping("/{id}")
        public void update(@RequestBody ComicBook comicBook, @PathVariable int id) {
            comicBookService.update(comicBook,id);
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}")
        public void delete(@PathVariable int id){
            comicBookService.delete(id);
        }

}
