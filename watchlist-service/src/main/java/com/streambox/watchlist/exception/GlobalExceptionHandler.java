package com.streambox.watchlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieServiceException.class)
    public ResponseEntity<String> handleMovieServiceException(
            MovieServiceException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<String> handleMovieAlreadyExists(
            MovieAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
