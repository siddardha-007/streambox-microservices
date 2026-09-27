package com.streambox.notification_service.event;

public record RatingCreatedEvent(
        Long ratingId,
        Long userId,
        Long movieId,
        Integer score,
        String comment
) {
}
