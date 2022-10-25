package com.example.comics;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Comicbook Already Exists")
public class ComicBookAlreadyExistsException extends RuntimeException {
    public ComicBookAlreadyExistsException(ComicBook comicBook) {
        super("Comicbook already exists: " + comicBook.toString());
    }
}
