package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.FullName;

public record ChangeCustomerFullNameCommand(CustomerId id, FullName newName) {
}
