package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.Email;

public record ChangeCustomerEmailCommand(CustomerId id, Email newEmail) {
}
