package com.example.domain;

import com.example.domain.exception.InvalidReasonException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reason {

    private static final int MAX_LENGTH = 500;
    private static final Reason NONE = new Reason("none");

    String value;

    public static Reason of(String reason) {
        if (reason == null || reason.isBlank()) {
            return NONE;
        }
        var normalized = reason.trim();

        if (normalized.length() > MAX_LENGTH) {
            throw new InvalidReasonException(
                    "Reason must not be longer than %d characters. Yours is %d"
                            .formatted(MAX_LENGTH, normalized.length())
            );
        }
        return new Reason(normalized);
    }

    public static Reason none() {
        return NONE;
    }
}
