package com.example.core.domain.exception;

public class InvalidPhoneNumberException extends DomainValidationException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
