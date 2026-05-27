package com.example.core.domain.exception;

import com.example.core.domain.valueobject.CustomerId;

public class CustomerNotBlockedException extends DomainValidationException {
    public CustomerNotBlockedException(CustomerId id) {
        super("Customer with id %s is not blocked".formatted(id.getValue()));
    }
}
