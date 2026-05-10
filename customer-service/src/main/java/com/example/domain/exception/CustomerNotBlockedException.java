package com.example.domain.exception;

import com.example.domain.CustomerId;

public class CustomerNotBlockedException extends DomainValidationException {
    public CustomerNotBlockedException(CustomerId id) {
        super("Customer with id %s is not blocked".formatted(id.getValue()));
    }
}
