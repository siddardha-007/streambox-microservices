package com.streambox.movie.client;

import com.streambox.movie.dto.TmdbMovieDetailsResponse;
import com.streambox.movie.dto.TmdbMovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TmdbClient {
    private final RestClient restClient;

    public TmdbClient(
            @Value("${tmdb.base-url}") String baseUrl,
            @Value("${tmdb.access-token}") String accessToken
    ){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
    }

    public TmdbMovieResponse getPopularMovies() {

        return restClient.get()
                .uri("/movie/popular")
                .retrieve()
                .body(TmdbMovieResponse.class);
    }

    public TmdbMovieDetailsResponse getMovieDetails(Long movieId) {

        return restClient.get()
                .uri("/movie/{id}", movieId)
                .retrieve()
                .body(TmdbMovieDetailsResponse.class);
    }

    public TmdbMovieResponse searchMovies(String query) {

        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .body(TmdbMovieResponse.class);
    }

    public TmdbMovieResponse getTrendingMovies() {

        return restClient.get()
                .uri("/trending/movie/day")
                .retrieve()
                .body(TmdbMovieResponse.class);
    }

    public TmdbMovieResponse getTopRatedMovies() {

        return restClient.get()
                .uri("/movie/top_rated")
                .retrieve()
                .body(TmdbMovieResponse.class);
    }

    public TmdbMovieResponse getSimilarMovies(Long movieId) {

        return restClient.get()
                .uri("/movie/{id}/similar", movieId)
                .retrieve()
                .body(TmdbMovieResponse.class);
    }
}
