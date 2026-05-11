package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.PhoneNumber;

public record ChangeCustomerPhoneCommand(CustomerId id, PhoneNumber newPhone) {
}
