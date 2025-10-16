package com.example.bankopsapi.exception.invalid;

public class InvalidIssuerIdException extends RuntimeException {
    public InvalidIssuerIdException() { super("Invalid issuer id."); }
}
