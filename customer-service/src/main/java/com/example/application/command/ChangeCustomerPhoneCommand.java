package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.PhoneNumber;

public record ChangeCustomerPhoneCommand(CustomerId id, PhoneNumber newPhone) {
}
