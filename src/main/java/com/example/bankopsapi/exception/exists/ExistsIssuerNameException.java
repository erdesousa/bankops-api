package com.example.bankopsapi.exception.exists;

public class ExistsIssuerNameException extends RuntimeException {
    public ExistsIssuerNameException() { super("There is already a registered issuer with this name."); }
}
