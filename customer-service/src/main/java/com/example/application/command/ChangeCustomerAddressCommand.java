package com.example.application.command;

import com.example.domain.Address;
import com.example.domain.CustomerId;

public record ChangeCustomerAddressCommand(CustomerId id, Address newAddress) {
}
