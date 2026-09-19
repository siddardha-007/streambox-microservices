package com.streambox.movie.dto;

public record MovieResponse(
        Long id,
        String title,
        String overview,
        String posterPath,
        String releaseDate,
        Double rating
) {
}
