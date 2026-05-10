package com.example.domain.exception;

import com.example.domain.CustomerId;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(CustomerId id) {
        super("Customer with id %s not found".formatted(id.getValue()));
    }
}
