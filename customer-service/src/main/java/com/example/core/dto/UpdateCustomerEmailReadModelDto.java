package com.example.core.dto;

import java.util.UUID;

public record UpdateCustomerEmailReadModelDto(UUID customerId, String newEmail) {
}
