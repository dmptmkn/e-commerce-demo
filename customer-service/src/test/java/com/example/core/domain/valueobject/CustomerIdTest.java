package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InvalidCustomerIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerIdTest {

    @Test
    void shouldCreate_whenIdValid() {
        var id = UUID.randomUUID();
        var customerId = CustomerId.of(id);

        assertThat(id).isEqualTo(customerId.getValue());
    }

    @Test
    void shouldThrowException_whenIdIsNull() {
        assertThatThrownBy(() -> CustomerId.of(null))
                .isInstanceOf(InvalidCustomerIdException.class)
                .hasMessage("Customer id is required");
    }
}
