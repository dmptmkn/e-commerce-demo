package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InsufficientLoyaltyPointsException;
import com.example.core.domain.exception.InvalidLoyaltyPointsFormatException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoyaltyPoints {

    public static LoyaltyPoints ZERO = new LoyaltyPoints(0);

    int value;

    public static LoyaltyPoints of(int points) {
        checkNotNegative(points);
        return new LoyaltyPoints(points);
    }

    public LoyaltyPoints add(LoyaltyPoints other) {
        return of(this.value + other.value);
    }

    public LoyaltyPoints add(int points) {
        checkNotNegative(points);
        return of(this.value + points);
    }

    public LoyaltyPoints subtract(int points) {
        checkNotNegative(points);

        var newValue = this.value - points;
        if (newValue < 0) {
            throw new InsufficientLoyaltyPointsException(this.value, points);
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

    private static void checkNotNegative(int points) {
        if (points < 0) {
            throw new InvalidLoyaltyPointsFormatException("Loyalty points must not be negative");
        }
    }
}
