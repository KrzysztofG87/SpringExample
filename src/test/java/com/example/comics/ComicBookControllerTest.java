package com.example.comics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComicBookControllerTest {

    // test-rest-template
    //
    @Autowired
    TestRestTemplate template;
    // test-find-all
    @Test
    void shouldReturnAllComicBooks() {
        ResponseEntity<ComicBook[]> entity = template.getForEntity("/comicBooks", ComicBook[].class);

        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,entity.getHeaders().getContentType());

        ComicBook[] comicBooks = entity.getBody();
        assertTrue(comicBooks.length >= 3);
        assertEquals("Hawkeye vs. Deadpool",comicBooks[0].getTitle());
        assertEquals("Guardians of the Galaxy: The Final Gauntlet",comicBooks[1].getTitle());
        assertEquals("Annihilation Omnibus Vol 1 1",comicBooks[2].getTitle());
    }
    // test-find-all-exchange
    @Test
    void shouldReturnAllComicBooksUsingExchange() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<List<ComicBook>> entity = template.exchange("/comicBooks", HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<ComicBook>>() {});

        assertEquals(HttpStatus.OK,entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON,entity.getHeaders().getContentType());

        List<ComicBook> comicBooks = entity.getBody();
        assertTrue(comicBooks.size() >= 3);
    }
    // test-find-one
    @Test
    void shouldReturnAValidComicBook() {
        ComicBook comicBook = template.getForObject("/comicBooks/1", ComicBook.class);
        assertEquals(1,comicBook.getId());
        assertEquals("Hawkeye vs. Deadpool",comicBook.getTitle());
        assertEquals("Gerry Duggan",comicBook.getAuthor());
        assertEquals("Marvel Comics",comicBook.getPublisher());
        assertEquals("September 2014", comicBook.getReleaseDate());
    }
    // test-find-one-invalid
    @Test
    void invalidBookIdShouldReturn404() {
        ResponseEntity<ComicBook> entity = template.getForEntity("/comicBooks/99", ComicBook.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }
    // test-create
    @Test
    void shouldCreateNewComicBook() {
        ComicBook comicBook = new ComicBook(4,
                "Fundamentals of Software Architecture: An Engineering Approach",
                "Mark Richards, Neal Ford",
                "Upfront Books",
                "Feb 2021");
        ResponseEntity<ComicBook> entity = template.postForEntity("/comicBooks", comicBook, ComicBook.class);
        assertEquals(HttpStatus.CREATED,entity.getStatusCode());

        ComicBook created = entity.getBody();

        assertEquals(4,created.getId());
        assertEquals("Fundamentals of Software Architecture: An Engineering Approach",created.getTitle());
        assertEquals("Mark Richards, Neal Ford",created.getAuthor());
        assertEquals("Upfront Books",created.getPublisher());
        assertEquals("Feb 2021", created.getReleaseDate());
        assertEquals(4,template.getForObject("/comicBooks", List.class).stream().count());
    }
}
