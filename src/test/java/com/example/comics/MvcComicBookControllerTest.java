package com.example.comics;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComicBookController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@ContextConfiguration(locations = "/applicationcontext.xml")
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
class MvcComicBookControllerTest {

    // mock-mvc

    @Autowired
    MockMvc mvc;

    @MockBean
    ComicBookService comicBookService;

    @MockBean
    JdbcTemplate jdbcTemplate;

    //ComicBookService mock = org.mockito.Mockito.mock(ComicBookService.class);

    @BeforeAll
    public void loadComicBooks() {
        comicBookService.loadComicBooks();
    }

    @Test
    void findAllShouldReturnAllComicBooks() throws Exception {
        Mockito.when(this.comicBookService.list()).thenReturn(getComicBooks());
        System.out.println("serwis: "+comicBookService.list());
        System.out.println("mock: "+getComicBooks());

        mvc.perform(get("/comicBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findOneShouldReturnValidComicBook() throws Exception {
        Mockito.when(this.comicBookService.get(1)).thenReturn(getComicBooks().get(0));

        mvc.perform(get("/comicBooks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Hawkeye vs. Deadpool"))
                .andExpect(jsonPath("$.author").value("Gerry Duggan"))
                .andExpect(jsonPath("$.publisher").value("Marvel Comics"))
                .andExpect(jsonPath("$.releaseDate").value("September 2014"));
    }

    private List<ComicBook> getComicBooks() {
        ComicBook one = new ComicBook(1,
                "Hawkeye vs. Deadpool",
                "Gerry Duggan",
                "Marvel Comics",
                "September 2014");
        ComicBook two = new ComicBook(2,
                "Guardians of the Galaxy: The Final Gauntlet",
                "Donny Cates",
                "Marvel Comics",
                "January 2019");
        return List.of(one, two);
    }
}