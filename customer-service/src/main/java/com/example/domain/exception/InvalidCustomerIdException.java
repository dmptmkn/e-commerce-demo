package com.example.domain.exception;

public class InvalidCustomerIdException extends DomainValidationException {
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}