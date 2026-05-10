package com.example.domain.exception;

import com.example.domain.CustomerId;

public class CustomerAlreadyBlockedException extends DomainValidationException {
    public CustomerAlreadyBlockedException(CustomerId id) {
        super("Customer with id %s is already blocked".formatted(id.getValue()));
    }
}
