package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InvalidCustomerIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerIdTest {

    @Test
    void shouldCreate_whenIdValid() {
        var id = UUID.randomUUID();
        var customerId = CustomerId.of(id);

        assertEquals(id, customerId.getValue());
    }

    @Test
    void shouldThrowException_whenIdIsNull() {
        assertThatThrownBy(() -> CustomerId.of(null))
                .isInstanceOf(InvalidCustomerIdException.class)
                .hasMessage("Customer id is required");
    }
}
