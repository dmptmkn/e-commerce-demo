package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InvalidCustomerIdException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerId {

    UUID value;

    public static CustomerId of(UUID id) {
        if (id == null) {
            throw new InvalidCustomerIdException("Customer id is required");
        }

        return new CustomerId(id);
    }
}
