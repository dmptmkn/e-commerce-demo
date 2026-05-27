package com.example.core.domain.exception;

import com.example.core.domain.valueobject.PhoneNumber;

public class SamePhoneNumberException extends DomainValidationException {
    public SamePhoneNumberException(PhoneNumber newPhone) {
        super("Phone number %s is already the same as the current one".formatted(newPhone.getValue()));
    }
}
