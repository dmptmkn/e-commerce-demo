package com.example.domain.exception;

import com.example.domain.PhoneNumber;

public class PhoneNumberAlreadyExistsException extends DomainConflictException {
    public PhoneNumberAlreadyExistsException(PhoneNumber phone) {
        super("Phone number %s is already taken".formatted(phone.getValue()));
    }
}
