package com.example.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import static com.example.domain.util.Preconditions.requireNotBlank;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    String country;
    String zipcode;
    String city;
    String street;
    String building;
    String apartment;

    public static Address of(
            String country,
            String zipcode,
            String city,
            String street,
            String building,
            String apartment) {
        requireNotBlank(country, "Country is required");
        requireNotBlank(zipcode, "Zipcode is required");
        requireNotBlank(city, "City is required");
        requireNotBlank(street, "Street is required");
        requireNotBlank(building, "Building is required");
        return new Address(country, zipcode, city, street, building, apartment);
    }
}