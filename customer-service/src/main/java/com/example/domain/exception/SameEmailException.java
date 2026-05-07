package com.example.domain.exception;

import com.example.domain.Email;

public class SameEmailException extends DomainException {
    public SameEmailException(Email newEmail) {
        super("Email %s is already the same as current one".formatted(newEmail.getValue()));
    }
}
