package com.example.core.domain.exception;

public class InvalidAddressException extends DomainValidationException {
    public InvalidAddressException(String message) {
        super(message);
    }
}
