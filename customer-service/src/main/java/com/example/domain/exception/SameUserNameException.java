package com.example.domain.exception;

import com.example.domain.UserName;

public class SameUserNameException extends DomainValidationException {
    public SameUserNameException(UserName newName) {
        super("User name %s is already the same as current one".formatted(newName.getValue()));
    }
}
