package com.example.bankopsapi.exception.notfound;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException() { super("Issuer not found."); }
}
