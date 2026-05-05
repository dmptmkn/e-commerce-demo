package com.example.domain.exception;

public class InsufficientLoyaltyPointsException extends DomainException {
    public InsufficientLoyaltyPointsException(String message) {
        super(message);
    }
}
