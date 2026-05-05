package com.example.domain.exception;

public class SamePhoneNumberException extends DomainException {
    public SamePhoneNumberException(String message) {
        super(message);
    }
}
