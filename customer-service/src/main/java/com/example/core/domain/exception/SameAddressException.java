package com.example.core.domain.exception;

import com.example.core.domain.valueobject.Address;

public class SameAddressException extends DomainValidationException {
    public SameAddressException(Address newAddress) {
        super("Address %s is already the same as current one".formatted(newAddress));
    }
}
