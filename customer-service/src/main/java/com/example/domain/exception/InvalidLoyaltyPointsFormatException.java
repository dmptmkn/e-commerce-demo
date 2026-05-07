package com.example.domain.exception;

public class InvalidLoyaltyPointsFormatException extends DomainValidationException {
    public InvalidLoyaltyPointsFormatException(String message) { super(message); }
}
