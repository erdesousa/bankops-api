package com.example.bankopsapi.exception.handlers;

import com.example.bankopsapi.exception.CardNotFoundException;
import com.example.bankopsapi.exception.IssuerNotFoundException;
import com.example.bankopsapi.exception.messages.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IssuerNotFoundException.class)
    private ResponseEntity<RestErrorMessage> issuerNotFoundException(IssuerNotFoundException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonErrorResponse);
    }

    @ExceptionHandler(CardNotFoundException.class)
    private ResponseEntity<RestErrorMessage> cardNotFoundException(CardNotFoundException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonErrorResponse);
    }
}
