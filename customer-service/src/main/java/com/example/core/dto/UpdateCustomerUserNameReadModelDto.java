package com.example.core.dto;

import java.util.UUID;

public record UpdateCustomerUserNameReadModelDto(UUID id, String newName) {
}
