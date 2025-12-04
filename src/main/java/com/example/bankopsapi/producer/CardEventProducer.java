package com.example.bankopsapi.producer;

import com.example.bankopsapi.config.KafkaConfig;
import com.example.bankopsapi.dto.event.CardEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardEventProducer {

    private final KafkaTemplate<String, CardEventDTO> kafkaTemplate;

    /**
     * Sends the card creation event to the Kafka topic asynchronously.
     *
     * @param event The DTO of the card event.
     */
    public void sendCardCreatedEvent(CardEventDTO event) {
        log.info("Sending card creation event to Kafka: {}", event);

        CompletableFuture<Void> future = kafkaTemplate.send(KafkaConfig.CARD_EVENTS_TOPIC, String.valueOf(event.cardId()), event)
                .thenAccept(result -> {
                    log.info("Event successfully sent to topic {} with offset {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().offset());
                })
                .exceptionally(ex -> {
                    log.error("Failed to send event to Kafka for ID card {}: {}", event.cardId(), ex.getMessage());
                    return null;
                });
    }
}