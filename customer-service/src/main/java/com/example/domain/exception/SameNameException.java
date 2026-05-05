package com.example.domain.exception;

public class SameNameException extends DomainException {
    public SameNameException(String message) {
        super(message);
    }
}
