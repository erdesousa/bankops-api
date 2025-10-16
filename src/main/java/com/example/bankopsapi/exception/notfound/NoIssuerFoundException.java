package com.example.bankopsapi.exception.notfound;

public class NoIssuerFoundException extends RuntimeException {
    public NoIssuerFoundException() { super("No issuers found."); }
}
