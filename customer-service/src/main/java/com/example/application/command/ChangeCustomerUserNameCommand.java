package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.UserName;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerUserNameCommand(@NotNull CustomerId id, @NotNull UserName newName) {
}
