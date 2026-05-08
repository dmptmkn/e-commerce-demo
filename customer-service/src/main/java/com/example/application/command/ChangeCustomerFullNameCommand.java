package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.FullName;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerFullNameCommand(@NotNull CustomerId id, @NotNull FullName newName) {
}
