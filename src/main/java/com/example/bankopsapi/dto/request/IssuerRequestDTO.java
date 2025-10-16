package com.example.bankopsapi.dto.request;

public record IssuerRequestDTO(
        String bin,
        String name,
        String flag,
        String country
) {}
