package com.streambox.movie.service;

import com.streambox.movie.client.TmdbClient;
import com.streambox.movie.dto.MovieDetailsResponse;
import com.streambox.movie.dto.MovieResponse;
import com.streambox.movie.dto.TmdbMovieDetailsResponse;
import com.streambox.movie.dto.TmdbMovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final TmdbClient tmdbClient;

    public List<MovieResponse> getPopularMovies() {

        TmdbMovieResponse response = tmdbClient.getPopularMovies();
        return response.results()
                .stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    public MovieDetailsResponse getMovieDetails(Long movieId) {

        TmdbMovieDetailsResponse movie =
                tmdbClient.getMovieDetails(movieId);

        return new MovieDetailsResponse(
                movie.id(),
                movie.title(),
                movie.overview(),
                movie.posterPath(),
                movie.backdropPath(),
                movie.releaseDate(),
                movie.runtime(),
                movie.rating()
        );
    }

    public List<MovieResponse> searchMovies(String query) {

        TmdbMovieResponse response =
                tmdbClient.searchMovies(query);

        return response.results()
                .stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    public List<MovieResponse> getTrendingMovies() {

        TmdbMovieResponse response =
                tmdbClient.getTrendingMovies();

        return response.results()
                .stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    public List<MovieResponse> getTopRatedMovies() {

        TmdbMovieResponse response =
                tmdbClient.getTopRatedMovies();

        return response.results()
                .stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    public List<MovieResponse> getSimilarMovies(Long movieId) {

        TmdbMovieResponse response =
                tmdbClient.getSimilarMovies(movieId);

        return response.results()
                .stream()
                .map(this::mapToMovieResponse)
                .toList();
    }

    // Helper methods

    private MovieResponse mapToMovieResponse(
            TmdbMovieResponse.TmdbMovie movie
    ) {
        return new MovieResponse(
                movie.id(),
                movie.title(),
                movie.overview(),
                movie.posterPath(),
                movie.releaseDate(),
                movie.rating()
        );
    }
}
