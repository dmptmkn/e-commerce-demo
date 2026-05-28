package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.UserName;

public record ChangeCustomerUserNameCommand(CustomerId id, UserName newName) {
}
