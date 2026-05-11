package com.example.infrastructure.web.dto;

public record RegisterCustomerDto(String email,
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
