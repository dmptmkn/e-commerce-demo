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
        if (isBlankOrNull(country)) {
            throw new InvalidAddressException("Country is required");
        }
        var normalizedCountry = country.trim().toUpperCase();
        if (normalizedCountry.length() > COUNTRY_MAX_LENGTH) {
            throw new InvalidAddressException("Country name is too long");
        }
        this.country = normalizedCountry;

        if (isBlankOrNull(zipcode)) {
            throw new InvalidAddressException("Zipcode is required");
        }
        var normalizedZipcode = zipcode.trim().toUpperCase();
        if (!ZIPCODE_PATTERN.matcher(normalizedZipcode).matches()) {
            throw new InvalidAddressException("Zipcode format is invalid");
        }
        if (normalizedZipcode.length() > ZIPCODE_MAX_LENGTH) {
            throw new InvalidAddressException("Zipcode is too long");
        }
        this.zipcode = normalizedZipcode;

        if (isBlankOrNull(city)) {
            throw new InvalidAddressException("City is required");
        }
        var normalizedCity = city.trim().toUpperCase();
        if (normalizedCity.length() > CITY_MAX_LENGTH) {
            throw new InvalidAddressException("City name is too long");
        }
        this.city = normalizedCity;

        if (isBlankOrNull(street)) {
            throw new InvalidAddressException("Street is required");
        }
        var normalizedStreet = street.trim().toUpperCase();
        if (normalizedStreet.length() > STREET_MAX_LENGTH) {
            throw new InvalidAddressException("Street name is too long");
        }
        this.street = normalizedStreet;

        if (isBlankOrNull(building)) {
            throw new InvalidAddressException("Building is required");
        }
        var normalizedBuilding = building.trim().toUpperCase();
        if (normalizedBuilding.length() > BUILDING_MAX_LENGTH) {
            throw new InvalidAddressException("Building number is too long");
        }
        this.building = normalizedBuilding;

        var normalizedApartmentOrNull = apartment == null ? null : apartment.trim().toUpperCase();
        if (normalizedApartmentOrNull != null &&
                normalizedApartmentOrNull.length() > APARTMENT_MAX_LENGTH) {
            throw new InvalidAddressException("Apartment number is too long");
        }
        this.apartment = normalizedApartmentOrNull;
    }

    private boolean isBlankOrNull(String field) {
        return field == null || field.isBlank();
    }
}