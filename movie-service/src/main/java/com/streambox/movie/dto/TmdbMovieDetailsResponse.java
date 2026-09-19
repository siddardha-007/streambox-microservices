package com.streambox.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TmdbMovieDetailsResponse(
        Long id,
        String title,
        String overview,

        @JsonProperty("poster_path")
        String posterPath,

        @JsonProperty("backdrop_path")
        String backdropPath,

        @JsonProperty("release_date")
        String releaseDate,

        Integer runtime,

        @JsonProperty("vote_average")
        Double rating
) {
}
