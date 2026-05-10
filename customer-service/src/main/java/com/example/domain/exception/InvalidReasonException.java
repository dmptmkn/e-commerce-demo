package com.example.domain.exception;

public class InvalidReasonException extends DomainValidationException {

    public InvalidReasonException(String message) {
        super(message);
    }
}
