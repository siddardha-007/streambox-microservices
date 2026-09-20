package com.streambox.watchlist.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class MovieServiceErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        System.out.println(
                "Feign error - status: " + response.status()
                        + ", method: " + methodKey
        );

        if (response.status() == 404) {
            return new MovieServiceException("Movie not found");
        }

        if (response.status() >= 500) {
            return new MovieServiceException(
                    "Movie service returned " + response.status()
            );
        }

        return new MovieServiceException(
                "Movie service returned " + response.status()
        );
    }
}
