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
     * Escuta o tópico de eventos de cartão e processa a mensagem.
     *
     * @param record O registro do consumidor contendo a mensagem.
     */
    @KafkaListener(topics = KafkaConfig.CARD_EVENTS_TOPIC, groupId = "card-processor-group")
    public void listenCardEvents(ConsumerRecord<String, CardEventDTO> record) {
        CardEventDTO event = record.value();

        log.info("=================================================================================");
        log.info("EVENTO KAFKA RECEBIDO: Tópico={}, Chave={}, Offset={}",
                record.topic(), record.key(), record.offset());
        log.info("Processando evento de criação de cartão: {}", event);
        log.info("=================================================================================");

        // --- Lógica de Negócio Assíncrona ---
        // Aqui você implementaria a lógica que não precisa ser síncrona, como:
        // 1. Envio de notificação por e-mail/SMS
        // 2. Registro de log de auditoria em um banco de dados secundário
        // 3. Chamada a um serviço de análise de risco
        // -------------------------------------

        // Exemplo de lógica:
        log.info("Simulando processamento assíncrono para o Cartão ID: {} (Emissor: {})",
                event.cardId(), event.issuerId());

        // O log de sucesso é a sua "interface" para ver o funcionamento!
    }
}
