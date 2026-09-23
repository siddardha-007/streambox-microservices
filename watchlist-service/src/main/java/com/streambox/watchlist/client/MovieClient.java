package com.streambox.watchlist.client;

import com.streambox.watchlist.config.FeignConfig;
import com.streambox.watchlist.dto.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "movie-service",
        configuration = FeignConfig.class
)
public interface MovieClient {
    @GetMapping("/movies/{id}")
    MovieResponse getMovieById(
            @PathVariable("id") Long movieId
    );
}
