package com.example.comics;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ComicBookNotFoundException extends RuntimeException {

    public ComicBookNotFoundException(int id) {
        super("ComicBook not found with id: " + id);
    }
}
