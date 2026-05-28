package com.example.core.domain.exception;

import com.example.core.domain.valueobject.Email;

public class EmailAlreadyExistsException extends DomainConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email %s is already taken".formatted(email.getValue()));
    }
}
