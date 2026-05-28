package com.example.core.domain.exception;

public class InvalidCustomerNameException extends DomainValidationException {
    public InvalidCustomerNameException(String message) {
        super(message);
    }
}
