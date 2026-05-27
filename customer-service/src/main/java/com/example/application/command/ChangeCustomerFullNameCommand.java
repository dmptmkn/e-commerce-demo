package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.FullName;

public record ChangeCustomerFullNameCommand(CustomerId id, FullName newName) {
}
