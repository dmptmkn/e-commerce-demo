package com.example.core.domain.exception;

import com.example.core.domain.valueobject.Email;

public class SameEmailException extends DomainValidationException {
    public SameEmailException(Email newEmail) {
        super("Email %s is already the same as current one".formatted(newEmail.getValue()));
    }
}
