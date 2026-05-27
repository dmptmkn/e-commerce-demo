package com.example.core.dto;

import java.util.UUID;

public record UpdateCustomerPhoneReadModelDto(UUID customerId, String newPhone) {
}

