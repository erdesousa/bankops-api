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
     * Envia o evento de criação de cartão para o tópico Kafka de forma assíncrona.
     *
     * @param event O DTO do evento de cartão.
     */
    public void sendCardCreatedEvent(CardEventDTO event) {
        log.info("Enviando evento de criação de cartão para o Kafka: {}", event);

        // Envio assíncrono da mensagem
        CompletableFuture<Void> future = kafkaTemplate.send(KafkaConfig.CARD_EVENTS_TOPIC, String.valueOf(event.cardId()), event)
                .thenAccept(result -> {
                    // Log de sucesso
                    log.info("Evento enviado com sucesso para o tópico {} com offset {}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().offset());
                })
                .exceptionally(ex -> {
                    // Log de falha
                    log.error("Falha ao enviar evento para o Kafka para o cartão ID {}: {}", event.cardId(), ex.getMessage());
                    return null;
                });
    }
}