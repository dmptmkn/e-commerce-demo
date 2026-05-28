package com.example.core.domain.exception;

import com.example.core.domain.valueobject.CustomerId;

public class CustomerAlreadyBlockedException extends DomainValidationException {
    public CustomerAlreadyBlockedException(CustomerId id) {
        super("Customer with id %s is already blocked".formatted(id.getValue()));
    }
}
