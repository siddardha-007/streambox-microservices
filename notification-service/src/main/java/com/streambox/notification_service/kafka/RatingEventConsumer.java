package com.streambox.notification_service.kafka;

import com.streambox.notification_service.event.RatingCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RatingEventConsumer {

    @KafkaListener(
            topics = "rating-created",
            groupId = "notification-service-group"
    )
    public void consumeRatingCreated(RatingCreatedEvent event) {

        log.info("==========================================");
        log.info("New Rating Event Received!");
        log.info("Rating ID : {}", event.ratingId());
        log.info("User ID   : {}", event.userId());
        log.info("Movie ID  : {}", event.movieId());
        log.info("Score     : {}", event.score());
        log.info("==========================================");
    }
}
