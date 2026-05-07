package com.example.domain.exception;

import com.example.domain.Email;

public class EmailAlreadyExistsException extends DomainConflictException {
    public EmailAlreadyExistsException(Email email) {
        super("Email %s is already taken".formatted(email.getValue()));
    }
}
