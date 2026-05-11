package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.Reason;

public record UnblockCustomerCommand(CustomerId id, Reason reason) {
}
