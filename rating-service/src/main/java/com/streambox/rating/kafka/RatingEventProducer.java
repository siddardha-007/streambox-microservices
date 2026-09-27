package com.streambox.rating.kafka;

import com.streambox.rating.event.RatingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class RatingEventProducer {
    private static final String TOPIC = "rating-created";

    private final KafkaTemplate<String, RatingCreatedEvent> kafkaTemplate;

    public void publishRatingCreated(RatingCreatedEvent event){
        kafkaTemplate.send(
                TOPIC,
                event.ratingId().toString(),
                event
        );
    }
}
