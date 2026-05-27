package com.example.application.command;

import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.CustomerId;

public record ChangeCustomerAddressCommand(CustomerId id, Address newAddress) {
}
