package com.example.adapter.in.rest.dto;

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
