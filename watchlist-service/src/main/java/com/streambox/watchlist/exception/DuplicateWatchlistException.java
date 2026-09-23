package com.streambox.watchlist.exception;

public class DuplicateWatchlistException extends RuntimeException {
    public DuplicateWatchlistException(String message) {
        super(message);
    }
}
