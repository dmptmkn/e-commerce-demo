package com.example.domain;

import com.example.domain.exception.InvalidCustomerNameException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerName {

    private static final int MAX_LENGTH = 255;

    String value;

    public static CustomerName of(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidCustomerNameException("Customer name is required");
        }

        var normalized = name.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new InvalidCustomerNameException(
                    "Customer name must not be longer than %d characters. Yours is %d"
                            .formatted(MAX_LENGTH, normalized.length()));
        }

        return new CustomerName(normalized);
    }
}
