package com.example.bankopsapi.exception.notfound;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() { super("Card not found."); }
}
