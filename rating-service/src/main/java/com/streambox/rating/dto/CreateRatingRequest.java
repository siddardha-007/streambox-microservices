package com.streambox.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRatingRequest(
        @NotNull
        Long movieId,

        @NotNull
        @Min(1)
        @Max(5)
        Integer score,

        String comment
) {
}
