package com.example.core.domain.exception;

public class InsufficientLoyaltyPointsException extends DomainValidationException {
    public InsufficientLoyaltyPointsException(int currentBalance, int toBeSubtracted) {
        super("Insufficient loyalty points. Your current balance is %d, cannot subtract %d".formatted(
                currentBalance, toBeSubtracted
        ));
    }
}
