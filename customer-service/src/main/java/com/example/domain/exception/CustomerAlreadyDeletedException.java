package com.example.domain.exception;

import com.example.domain.CustomerId;

public class CustomerAlreadyDeletedException extends DomainValidationException {
    public CustomerAlreadyDeletedException(CustomerId id) {
        super("Customer with id %s is already deleted".formatted(id.getValue()));
    }
}
