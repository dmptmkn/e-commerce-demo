package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Reason;

public record UnblockCustomerCommand(CustomerId id, Reason reason) {
}
