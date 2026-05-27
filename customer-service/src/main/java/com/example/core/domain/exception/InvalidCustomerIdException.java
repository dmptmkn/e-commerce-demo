package com.example.core.domain.exception;

public class InvalidCustomerIdException extends DomainValidationException {
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}
