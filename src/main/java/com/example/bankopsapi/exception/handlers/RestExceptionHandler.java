package com.example.bankopsapi.exception.handlers;

import com.example.bankopsapi.exception.exists.ExistsCardIssuerException;
import com.example.bankopsapi.exception.exists.ExistsIssuerNameException;
import com.example.bankopsapi.exception.invalid.InvalidCardIdException;
import com.example.bankopsapi.exception.invalid.InvalidIssuerIdException;
import com.example.bankopsapi.exception.messages.RestErrorMessage;
import com.example.bankopsapi.exception.notfound.CardNotFoundException;
import com.example.bankopsapi.exception.notfound.IssuerNotFoundException;
import com.example.bankopsapi.exception.notfound.NoCardFoundException;
import com.example.bankopsapi.exception.notfound.NoIssuerFoundException;
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

    @ExceptionHandler(NoCardFoundException.class)
    private ResponseEntity<RestErrorMessage> noCardFoundException(NoCardFoundException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonErrorResponse);
    }

    @ExceptionHandler(InvalidCardIdException.class)
    private ResponseEntity<RestErrorMessage> invalidCardIdException(InvalidCardIdException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonErrorResponse);
    }

    @ExceptionHandler(ExistsCardIssuerException.class)
    private ResponseEntity<RestErrorMessage> existsCardIssuerException(ExistsCardIssuerException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.CONFLICT ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonErrorResponse);
    }

    @ExceptionHandler(ExistsIssuerNameException.class)
    private ResponseEntity<RestErrorMessage> existsIssuerNameException(ExistsIssuerNameException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.CONFLICT ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonErrorResponse);
    }

    @ExceptionHandler(NoIssuerFoundException.class)
    private ResponseEntity<RestErrorMessage> noIssuerFoundException(NoIssuerFoundException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonErrorResponse);
    }

    @ExceptionHandler(InvalidIssuerIdException.class)
    private ResponseEntity<RestErrorMessage> invalidIssuerIdException(InvalidIssuerIdException exception) {
        RestErrorMessage jsonErrorResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST ,exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonErrorResponse);
    }
}
