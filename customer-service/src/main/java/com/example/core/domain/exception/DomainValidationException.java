package com.example.core.domain.exception;

public abstract class DomainValidationException extends DomainException {
    protected DomainValidationException(String message) {
        super(message);
    }
    protected DomainValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
