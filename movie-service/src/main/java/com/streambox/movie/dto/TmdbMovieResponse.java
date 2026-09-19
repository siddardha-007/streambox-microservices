package com.streambox.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbMovieResponse(
        int page,
        List<TmdbMovie> results
) {

    public record TmdbMovie(
            Long id,
            String title,
            String overview,

            @JsonProperty("poster_path")
            String posterPath,

            @JsonProperty("release_date")
            String releaseDate,

            @JsonProperty("vote_average")
            Double rating
    ){
    }
}
