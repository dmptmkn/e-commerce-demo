package com.example.domain.exception;

public class SameEmailException extends DomainException {
    public SameEmailException(String message) {
        super(message);
    }
}
