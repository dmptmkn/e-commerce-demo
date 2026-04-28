package com.example.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import static com.example.domain.util.Preconditions.requireMatches;
import static com.example.domain.util.Preconditions.requireNotBlank;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber {

    String value;

    public static PhoneNumber of(String rawNumber) {
        var normalized = rawNumber == null ? null : rawNumber.trim();
        requireNotBlank(normalized, "Phone number is required");

        var phoneRegex = "^\\+[1-9][0-9]{1,14}$";
        requireMatches(normalized, phoneRegex, "Invalid phone number format");

        return new PhoneNumber(normalized);
    }
}