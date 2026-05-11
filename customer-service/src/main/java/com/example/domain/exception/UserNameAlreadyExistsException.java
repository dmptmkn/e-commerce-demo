package com.example.domain.exception;

import com.example.domain.UserName;

public class UserNameAlreadyExistsException extends DomainConflictException {
    public UserNameAlreadyExistsException(UserName name) {
        super("Customer with user name %s already exists".formatted(name.getValue()));
    }
}
