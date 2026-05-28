package com.example.core.dto;

import java.util.UUID;

public record CreateCustomerReadModelDto(UUID customerId,
                                         String email,
                                         String phone,
                                         String userName,
                                         String firstName,
                                         String lastName,
                                         String country,
                                         String zipcode,
                                         String city,
                                         String street,
                                         String building,
                                         String apartment) {
}
