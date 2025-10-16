package com.example.bankopsapi.exception.exists;

public class ExistsCardIssuerException extends RuntimeException {
    public ExistsCardIssuerException() { super("A card already exists for this issuer."); }
}
