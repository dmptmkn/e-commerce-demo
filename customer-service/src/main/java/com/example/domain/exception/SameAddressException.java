package com.example.domain.exception;

public class SameAddressException extends DomainException {
    public SameAddressException(String message) {
        super(message);
    }
}
