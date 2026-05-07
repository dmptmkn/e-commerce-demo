package com.example.domain;

import com.example.domain.exception.InvalidAddressException;
import lombok.Builder;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Address {

    private static final String ZIPCODE_REGEX = "^[a-zA-Z0-9\\s-]+$";
    private static final Pattern ZIPCODE_PATTERN = Pattern.compile(ZIPCODE_REGEX);
    private static final int COUNTRY_MAX_LENGTH = 100;
    private static final int ZIPCODE_MAX_LENGTH = 20;
    private static final int CITY_MAX_LENGTH = 255;
    private static final int STREET_MAX_LENGTH = 255;
    private static final int BUILDING_MAX_LENGTH = 20;
    private static final int APARTMENT_MAX_LENGTH = 20;

    String country;
    String zipcode;
    String city;
    String street;
    String building;
    String apartment;

    @Builder
    public Address(
            String country,
            String zipcode,
            String city,
            String street,
            String building,
            String apartment) {
        checkIfNullOrBlank("Country", country);
        var normalizedCountry = country.trim();
        checkLength("Country name", normalizedCountry, COUNTRY_MAX_LENGTH);
        this.country = normalizedCountry;

        checkIfNullOrBlank("Zipcode", zipcode);
        var normalizedZipcode = zipcode.trim().toUpperCase();
        if (!ZIPCODE_PATTERN.matcher(normalizedZipcode).matches()) {
            throw new InvalidAddressException("Zipcode format is invalid");
        }
        checkLength("Zipcode", normalizedZipcode, ZIPCODE_MAX_LENGTH);
        this.zipcode = normalizedZipcode;

        checkIfNullOrBlank("City", city);
        var normalizedCity = city.trim();
        checkLength("City name", normalizedCity, CITY_MAX_LENGTH);
        this.city = normalizedCity;

        checkIfNullOrBlank("Street", street);
        var normalizedStreet = street.trim();
        checkLength("Street name", normalizedStreet, STREET_MAX_LENGTH);
        this.street = normalizedStreet;

        checkIfNullOrBlank("Building", building);
        var normalizedBuilding = building.trim().toUpperCase();
        checkLength("Building number", normalizedBuilding, BUILDING_MAX_LENGTH);
        this.building = normalizedBuilding;

        var normalizedApartment = apartment == null ? null : apartment.trim().toUpperCase();
        if (normalizedApartment != null) {
            checkLength("Apartment number", normalizedApartment, APARTMENT_MAX_LENGTH);
        }
        this.apartment = normalizedApartment;
    }

    private static void checkIfNullOrBlank(String fieldName, String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidAddressException("%s is required".formatted(fieldName));
        }
    }

    private static void checkLength(String fieldName, String value, int maxLength) {
        if (value.length() > maxLength) {
            throw new InvalidAddressException(
                    "%s must not be longer than %d characters, yours is %d"
                            .formatted(fieldName, maxLength, value.length())
            );
        }
    }
}