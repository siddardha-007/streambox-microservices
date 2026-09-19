package com.streambox.movie.dto;

public record MovieDetailsResponse(
        Long id,
        String title,
        String overview,
        String posterPath,
        String backdropPath,
        String releaseDate,
        Integer runtime,
        Double rating
) {
}
