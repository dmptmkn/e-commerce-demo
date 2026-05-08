package com.example.domain;

import com.example.domain.exception.InvalidEmailException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.regex.Pattern;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final int MAX_LENGTH = 255;

    String value;

    public static Email of(String rawEmail) {
        if (rawEmail == null || rawEmail.isBlank()) {
            throw new InvalidEmailException("Email is required");
        }

        var normalized = rawEmail.trim().toLowerCase();
        if (normalized.length() > MAX_LENGTH) {
            throw new InvalidEmailException(
                    "Email must not be longer than %d characters. Yours is %d"
                            .formatted(MAX_LENGTH, normalized.length())
            );
        }

        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new InvalidEmailException(
                    "Email %s doesn't match the required pattern".formatted(normalized));
        }

        return new Email(normalized);
    }
}
