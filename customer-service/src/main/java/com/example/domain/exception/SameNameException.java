package com.example.domain.exception;

import com.example.domain.CustomerName;

public class SameNameException extends DomainException {

    public SameNameException(CustomerName newName) {
        super("Name %s is already the same as current one".formatted(newName.getValue()));
    }
}
