package com.example.core.dto;

import java.util.UUID;

public record UpdateCustomerAddressReadModelDto(UUID customerId,
                                                String country,
                                                String zipcode,
                                                String city,
                                                String street,
                                                String building,
                                                String apartment) {
}
