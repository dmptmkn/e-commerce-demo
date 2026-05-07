package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.PhoneNumber;
import jakarta.validation.constraints.NotNull;

public record ChangeCustomerPhoneCommand(@NotNull CustomerId id, @NotNull PhoneNumber newPhone) {
}
