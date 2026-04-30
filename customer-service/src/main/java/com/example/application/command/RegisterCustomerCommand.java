package com.example.application.command;

public record RegisterCustomerCommand(String email,
                                      String phone,
                                      String name,
                                      String country,
                                      String zipcode,
                                      String city,
                                      String street,
                                      String building,
                                      String apartment) {
}