package com.example.core.domain.exception;

import com.example.core.domain.valueobject.CustomerId;

public class CustomerBlockedException extends DomainValidationException {
    public CustomerBlockedException(CustomerId id) {
        super("Customer %s is blocked".formatted(id.getValue()));
    }
}
