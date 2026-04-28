package com.example.domain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Preconditions {

    public static void requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireMatches(String value, String regex, String message) {
        requireNotBlank(value, message);
        if (!value.matches(regex)) {
            throw new IllegalArgumentException(message);
        }
    }
}