package com.example.domain;

import com.example.domain.exception.InsufficientLoyaltyPointsException;
import com.example.domain.exception.InvalidLoyaltyPointsFormatException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoyaltyPoints {

    public static LoyaltyPoints ZERO = new LoyaltyPoints(0);

    int value;

    public static LoyaltyPoints of(int points) {
        if (points < 0) {
            throw new InvalidLoyaltyPointsFormatException("Loyalty points must not be negative");
        }
        return new LoyaltyPoints(points);
    }

    public LoyaltyPoints add(LoyaltyPoints other) {
        return of(this.value + other.value);
    }

    public LoyaltyPoints add(int points) {
        return of(this.value + points);
    }

    public LoyaltyPoints subtract(int points) {
        if (points < 0) {
            throw new InvalidLoyaltyPointsFormatException("Loyalty points must not be negative");
        }

        var newValue = this.value - points;
        if (newValue < 0) {
            throw new InsufficientLoyaltyPointsException("Insufficient loyalty points");
        }

        return LoyaltyPoints.of(newValue);
    }

    public LoyaltyPoints subtract(LoyaltyPoints other) {
        return subtract(other.value);
    }

    public boolean isGreaterThan(LoyaltyPoints other) {
        return this.value > other.value;
    }

    public boolean isLessThan(LoyaltyPoints other) {
        return this.value < other.value;
    }

    public boolean isZero() {
        return value == 0;
    }
}