package com.streambox.rating.exception;

public class DuplicateRatingException extends RuntimeException {
    public DuplicateRatingException(String message) {
        super(message);
    }
}
