package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.Email;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerEmailCommand(@NotNull CustomerId id, @NotNull Email newEmail) {
}
