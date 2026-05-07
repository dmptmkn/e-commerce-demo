package com.example.domain.exception;

public class InvalidCustomerNameException extends DomainValidationException {
    public InvalidCustomerNameException(String message) {
        super(message);
    }
}
