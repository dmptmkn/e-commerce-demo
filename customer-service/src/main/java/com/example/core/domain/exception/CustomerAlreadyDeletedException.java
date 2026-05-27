package com.example.core.domain.exception;

import com.example.core.domain.valueobject.CustomerId;

public class CustomerAlreadyDeletedException extends DomainValidationException {
    public CustomerAlreadyDeletedException(CustomerId id) {
        super("Customer with id %s is already deleted".formatted(id.getValue()));
    }
}
