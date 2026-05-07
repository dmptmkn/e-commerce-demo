package com.example.domain.exception;

import com.example.domain.CustomerStatus;

public class SameStatusException extends DomainException {
    public SameStatusException(CustomerStatus newStatus) {
        super("Status %s is already the same as the current one".formatted(newStatus.name()));
    }
}
