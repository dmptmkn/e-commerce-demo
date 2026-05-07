package com.example.application.command;

import com.example.domain.Address;
import com.example.domain.CustomerId;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerAddressCommand(@NotNull CustomerId id, @NotNull Address newAddress) {
}
