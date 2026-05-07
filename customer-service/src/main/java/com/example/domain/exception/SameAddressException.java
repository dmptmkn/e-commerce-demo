package com.example.domain.exception;

import com.example.domain.Address;

public class SameAddressException extends DomainValidationException {
    public SameAddressException(Address newAddress) {
        super("Address %s is already the same as current one".formatted(newAddress));
    }
}
