package com.example.bankopsapi.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String CARD_EVENTS_TOPIC = "card-events";

    @Bean
    public NewTopic cardEventsTopic() {
        return TopicBuilder.name(CARD_EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
