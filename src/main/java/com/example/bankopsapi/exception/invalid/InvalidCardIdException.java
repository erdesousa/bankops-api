package com.example.bankopsapi.exception.invalid;

public class InvalidCardIdException extends RuntimeException {
    public InvalidCardIdException() { super("Invalid card id."); }
}
