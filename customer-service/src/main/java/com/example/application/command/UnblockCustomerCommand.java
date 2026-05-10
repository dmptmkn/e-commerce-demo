package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.Reason;
import jakarta.validation.constraints.NotNull;

public record UnblockCustomerCommand(@NotNull CustomerId id, @NotNull Reason reason) {
}
