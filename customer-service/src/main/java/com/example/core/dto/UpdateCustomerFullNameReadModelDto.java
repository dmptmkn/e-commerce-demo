package com.example.core.dto;

import java.util.UUID;

public record UpdateCustomerFullNameReadModelDto(UUID id, String firstName, String lastName) {
}
