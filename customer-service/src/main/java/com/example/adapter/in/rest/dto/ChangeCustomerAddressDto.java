package com.example.adapter.in.rest.dto;

public record ChangeCustomerAddressDto(String country,
                                       String zipcode,
                                       String city,
                                       String street,
                                       String building,
                                       String apartment) {
}
