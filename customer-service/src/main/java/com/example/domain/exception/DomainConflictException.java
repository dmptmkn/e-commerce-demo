package com.example.domain.exception;

public abstract class DomainConflictException extends DomainException {

    protected DomainConflictException(String message) {
        super(message);
    }

    protected DomainConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
