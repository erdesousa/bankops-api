package com.example.bankopsapi.consumer;

import com.example.bankopsapi.config.KafkaConfig;
import com.example.bankopsapi.dto.event.CardEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CardEventConsumer {

    /**
     * @param record The consumer record containing the message.
     */
    @KafkaListener(topics = KafkaConfig.CARD_EVENTS_TOPIC, groupId = "card-processor-group")
    public void listenCardEvents(ConsumerRecord<String, CardEventDTO> record) {
        CardEventDTO event = record.value();

        log.info("=================================================================================");
        log.info("KAFKA EVENT RECEIVED: Topic={}, Key={}, Offset={}",
                record.topic(), record.key(), record.offset());
        log.info("Processing card creation event: {}", event);
        log.info("=================================================================================");

        log.info("Simulating asynchronous processing for the ID Card: {} (Issuer: {})",
                event.cardId(), event.issuerId());
    }
}
