package com.example.core.domain.exception;

import com.example.core.domain.valueobject.CustomerId;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException() { super("Customer not found"); }
    public CustomerNotFoundException(String message) { super(message);};
    public CustomerNotFoundException(CustomerId id) {
        super("Customer with id %s not found".formatted(id.getValue()));
    }
}
