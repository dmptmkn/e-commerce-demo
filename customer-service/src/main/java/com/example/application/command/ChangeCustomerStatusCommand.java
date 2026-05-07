package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.CustomerStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerStatusCommand(@NotNull CustomerId id, @NotNull CustomerStatus newStatus) {
}
