package com.example.domain.exception;

public class InvalidCustomerIdException extends DomainException {
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}