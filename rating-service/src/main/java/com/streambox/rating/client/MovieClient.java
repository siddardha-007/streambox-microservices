package com.streambox.rating.client;

import com.streambox.rating.dto.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "movie-service"
)
public interface MovieClient {
    @GetMapping("/movies/{id}")
    MovieResponse getMovieById(
            @PathVariable("id") Long movieId
    );
}
