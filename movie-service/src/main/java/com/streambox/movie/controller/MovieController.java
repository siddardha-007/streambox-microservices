package com.streambox.movie.controller;

import com.streambox.movie.dto.MovieDetailsResponse;
import com.streambox.movie.dto.MovieResponse;
import com.streambox.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/health")
    public String health(){
        return "Movie service is running";
    }


    @GetMapping("/popular")
    public List<MovieResponse> getPopularMovies() {
        return movieService.getPopularMovies();
    }

    @GetMapping("/{id}")
    public MovieDetailsResponse getMovieDetails(
            @PathVariable Long id
    ) {
        return movieService.getMovieDetails(id);
    }

    @GetMapping("/search")
    public List<MovieResponse> searchMovies(
            @RequestParam String query
    ) {
        return movieService.searchMovies(query);
    }

    @GetMapping("/trending")
    public List<MovieResponse> getTrendingMovies() {
        return movieService.getTrendingMovies();
    }

    @GetMapping("/top-rated")
    public List<MovieResponse> getTopRatedMovies() {
        return movieService.getTopRatedMovies();
    }

    @GetMapping("/{id}/similar")
    public List<MovieResponse> getSimilarMovies(
            @PathVariable Long id
    ) {
        return movieService.getSimilarMovies(id);
    }
}
