package com.example.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import static com.example.domain.util.Preconditions.requireMatches;
import static com.example.domain.util.Preconditions.requireNotBlank;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    String value;

    public static Email of(String rawEmail) {
        var normalized = rawEmail == null ? null : rawEmail.trim().toLowerCase();
        requireNotBlank(normalized, "Email is required");

        var emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        requireMatches(normalized, emailRegex, "Invalid email format");

        return new Email(normalized);
    }
}