package com.streambox.notification_service.kafka;

import com.streambox.notification_service.event.RatingCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RatingEventConsumer {

    @KafkaListener(
            topics = "rating-created",
            groupId = "notification-group"
    )
    public void consumeRatingCreated(RatingCreatedEvent event){
        log.info(
                "Rating created event received: ratingId={}, userId={}, movieId={}, score={}",
                event.ratingId(),
                event.userId(),
                event.movieId(),
                event.score()
        );
    }
}
