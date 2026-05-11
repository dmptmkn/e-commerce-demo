package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.UserName;

public record ChangeCustomerUserNameCommand(CustomerId id, UserName newName) {
}
