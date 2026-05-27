package com.example.core.domain.exception;

public class InvalidReasonException extends DomainValidationException {

    public InvalidReasonException(String message) {
        super(message);
    }
}
