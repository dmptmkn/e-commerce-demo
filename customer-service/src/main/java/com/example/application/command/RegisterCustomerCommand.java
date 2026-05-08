package com.example.application.command;

import com.example.domain.Address;
import com.example.domain.FullName;
import com.example.domain.UserName;
import com.example.domain.Email;
import com.example.domain.PhoneNumber;
import jakarta.validation.constraints.NotNull;

public record RegisterCustomerCommand(@NotNull Email email,
                                      @NotNull PhoneNumber phone,
                                      @NotNull UserName userName,
                                      @NotNull FullName fullName,
                                      @NotNull Address address) {
}
