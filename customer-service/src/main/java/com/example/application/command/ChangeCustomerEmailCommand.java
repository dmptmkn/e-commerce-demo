package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Email;

public record ChangeCustomerEmailCommand(CustomerId id, Email newEmail) {
}
