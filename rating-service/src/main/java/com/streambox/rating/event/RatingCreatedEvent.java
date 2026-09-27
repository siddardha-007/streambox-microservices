package com.streambox.rating.event;

public record RatingCreatedEvent(
        Long ratingId,
        Long userId,
        Long movieId,
        Integer score,
        String comment
) {
}
