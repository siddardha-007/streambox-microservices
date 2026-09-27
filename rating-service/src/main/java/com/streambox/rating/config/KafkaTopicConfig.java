package com.streambox.rating.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ratingCreatedTopic(){
        return new NewTopic("rating-created",3,(short) 1);
    }
}
