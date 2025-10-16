package com.example.bankopsapi.exception;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException() { super("Issuer not found."); }
}
