package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.Reason;

public record BlockCustomerCommand(CustomerId id, Reason reason) {
}
