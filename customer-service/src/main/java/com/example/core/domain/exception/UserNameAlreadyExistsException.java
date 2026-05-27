package com.example.core.domain.exception;

import com.example.core.domain.valueobject.UserName;

public class UserNameAlreadyExistsException extends DomainConflictException {
    public UserNameAlreadyExistsException(UserName name) {
        super("Customer with user name %s already exists".formatted(name.getValue()));
    }
}
