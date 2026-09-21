package com.streambox.rating.dto;

import java.time.LocalDateTime;

public record RatingResponse(
        Long id,
        Long userId,
        Long movieId,
        Integer score,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
