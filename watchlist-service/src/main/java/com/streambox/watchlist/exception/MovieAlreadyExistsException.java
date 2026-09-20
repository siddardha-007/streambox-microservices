package com.streambox.watchlist.exception;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
