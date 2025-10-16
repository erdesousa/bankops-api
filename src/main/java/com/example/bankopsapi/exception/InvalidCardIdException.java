package com.example.bankopsapi.exception;

public class InvalidCardIdException extends RuntimeException {
    public InvalidCardIdException() { super("Invalid card id."); }
}
