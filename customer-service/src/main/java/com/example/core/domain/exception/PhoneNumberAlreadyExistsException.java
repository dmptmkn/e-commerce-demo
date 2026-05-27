package com.example.core.domain.exception;

import com.example.core.domain.valueobject.PhoneNumber;

public class PhoneNumberAlreadyExistsException extends DomainConflictException {
    public PhoneNumberAlreadyExistsException(PhoneNumber phone) {
        super("Phone number %s is already taken".formatted(phone.getValue()));
    }
}
