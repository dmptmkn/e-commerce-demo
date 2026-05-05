package com.example.domain.exception;

public class SameStatusException extends DomainException {
    public SameStatusException(String message) {
        super(message);
    }
}
