package com.example.bankopsapi.exception.notfound;

public class NoCardFoundException extends RuntimeException {
    public NoCardFoundException() { super("No cards found."); }
}
