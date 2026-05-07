package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.CustomerName;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerNameCommand(@NotNull CustomerId id, @NotNull CustomerName newName) {
}
