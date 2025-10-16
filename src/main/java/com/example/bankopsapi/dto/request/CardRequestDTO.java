package com.example.bankopsapi.dto.request;

public record CardRequestDTO(
        String name,
        String cardType,
        Integer pinAttempts,
        String validity,
        String securityCode,
        Boolean acceptApproach,
        Boolean onlinePurchase,
        Boolean cardInternational,
        Long issuerId
) {}
