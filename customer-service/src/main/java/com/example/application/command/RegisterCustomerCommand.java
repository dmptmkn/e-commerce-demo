package com.example.application.command;

import com.example.domain.Address;
import com.example.domain.CustomerName;
import com.example.domain.Email;
import com.example.domain.PhoneNumber;
import jakarta.validation.constraints.NotNull;

public record RegisterCustomerCommand(@NotNull Email email,
                                      @NotNull PhoneNumber phone,
                                      @NotNull CustomerName name,
                                      @NotNull Address address) {
}
