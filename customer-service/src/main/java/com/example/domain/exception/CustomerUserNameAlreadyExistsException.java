package com.example.domain.exception;

import com.example.domain.UserName;

public class CustomerUserNameAlreadyExistsException extends DomainConflictException {
    public CustomerUserNameAlreadyExistsException(UserName name) {
        super("Customer with user name %s already exists".formatted(name.getValue()));
    }
}
