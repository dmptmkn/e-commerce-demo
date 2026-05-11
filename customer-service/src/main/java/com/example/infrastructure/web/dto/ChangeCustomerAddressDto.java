package com.example.infrastructure.web.dto;

public record ChangeCustomerAddressDto(String country,
                                       String zipcode,
                                       String city,
                                       String street,
                                       String building,
                                       String apartment) {
}
