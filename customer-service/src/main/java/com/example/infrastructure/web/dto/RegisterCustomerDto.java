package com.example.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterCustomerDto(
        @NotBlank @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String name,
        @NotBlank
        String country,
        @NotBlank
        String zipcode,
        @NotBlank
        String city,
        @NotBlank
        String street,
        @NotBlank
        String building,
        String apartment
) {}