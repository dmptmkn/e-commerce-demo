package com.example.core.domain.exception;

import com.example.core.domain.valueobject.FullName;

public class SameFullNameException extends DomainValidationException {
    public SameFullNameException(FullName name) {
        super("Name %s is already the same as the current one".formatted(name.getFullName()));
    }
}
