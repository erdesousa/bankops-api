package com.example.bankopsapi.dto.event;

import java.time.LocalDateTime;

/**
 * @param cardId
 * @param cardName
 * @param issuerId .
 * @param eventTimestamp
 */
public record CardEventDTO(
        Long cardId,
        String cardName,
        Long issuerId,
        LocalDateTime eventTimestamp
) {}