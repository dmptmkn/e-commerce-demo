package com.example.domain;

import com.example.domain.exception.InvalidPhoneNumberException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.regex.Pattern;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber {

    private static final String PHONE_REGEX = "^\\+[1-9][0-9]{1,14}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final int MAX_LENGTH = 30;


    String value;

    public static PhoneNumber of(String rawNumber) {
        if (rawNumber == null || rawNumber.isBlank()) {
            throw new InvalidPhoneNumberException("Phone number is required");
        }

        var normalized = rawNumber.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new InvalidPhoneNumberException("Phone number is too long");
        }
        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number format");
        }

        return new PhoneNumber(normalized);
    }
}