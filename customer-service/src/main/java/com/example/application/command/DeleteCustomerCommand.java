package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Reason;

public record DeleteCustomerCommand(CustomerId id, Reason reason) {
}
