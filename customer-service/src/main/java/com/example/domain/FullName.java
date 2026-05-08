package com.example.domain;

import com.example.domain.exception.InvalidCustomerNameException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.regex.Pattern;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FullName {

    private static final int MAX_LENGTH = 100;
    private static final String NAME_REGEX = "^\\p{L}([\\p{L}' -]*\\p{L})?$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    String firstName;
    String lastName;

    public static FullName of(String firstName, String lastName) {
        var normalizedFirstName = validateAndNormalize(firstName, "First name");
        var normalizedLastName = validateAndNormalize(lastName, "Last name");

        return new FullName(normalizedFirstName, normalizedLastName);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    private static String validateAndNormalize(String value, String fieldName) {
        checkIfNullOrBlank(value, fieldName);
        var normalized = value.trim();
        checkLength(normalized, fieldName);
        checkIfMatches(normalized, fieldName);
        return normalized;
    }

    private static void checkIfNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidCustomerNameException("%s is required".formatted(fieldName));
        }
    }

    private static void checkLength(String value, String fieldName) {
        if (value.length() > MAX_LENGTH) {
            throw new InvalidCustomerNameException(
                    "%s must not be longer than %d characters, yours is %d"
                            .formatted(fieldName, MAX_LENGTH, value.length())
            );
        }
    }

    private static void checkIfMatches(String value, String fieldName) {
        if (!NAME_PATTERN.matcher(value).matches()) {
            throw new InvalidCustomerNameException(
                    "%s doesn't match the required pattern".formatted(fieldName)
            );
        }
    }
}
