package com.example.domain.exception;

import com.example.domain.CustomerName;

public class CustomerNameAlreadyExistsException extends DomainException {
    public CustomerNameAlreadyExistsException(CustomerName name) {
        super("Customer with name %s already exists".formatted(name.getValue()));
    }
}
