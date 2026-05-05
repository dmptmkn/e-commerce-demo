package com.example.domain.exception;

public class InvalidCustomerNameException extends DomainException {
    public InvalidCustomerNameException(String message) {
        super(message);
    }
}
