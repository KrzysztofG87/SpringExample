package com.example.comics;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

    @SpringBootTest(classes = ComicsApplication.class)
//@ExtendWith(ProductServiceTestConfiguration.class)
    @ActiveProfiles("test")
    @RunWith(SpringJUnit4ClassRunner.class)
    public class ComicsApplicationUnitTests {
        @Autowired
        private ComicBookService comicBookService;

        @Test
        public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
            //TODO spr
            Mockito.when(comicBookService.get(1).getTitle()).thenReturn("Hawkeye vs. Deadpool");
            String testName = "Hawkeye vs. Deadpool";
            Assert.assertEquals("Hawkeye vs. Deadpool", testName);
        }
    }
