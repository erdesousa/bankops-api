package com.example.bankopsapi.exception;

public class NoCardFoundException extends RuntimeException {
    public NoCardFoundException() { super("No cards found."); }
}
