package com.example.bankopsapi.exception;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() { super("Card not found."); }
}
