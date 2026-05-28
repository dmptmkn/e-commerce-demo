package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InvalidAddressException;
import lombok.Builder;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Address {

    private static final String ZIPCODE_REGEX = "^[a-zA-Z0-9\\s-]+$";
    private static final String ADDRESS_TEXT_REGEX = "^[\\p{L}\\p{N}\\s\\-.,'/#]+$";
    private static final Pattern ZIPCODE_PATTERN = Pattern.compile(ZIPCODE_REGEX);
    private static final Pattern ADDRESS_TEXT_PATTERN = Pattern.compile(ADDRESS_TEXT_REGEX);
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
        this.country = validateAndNormalize(
                country,
                COUNTRY_MAX_LENGTH,
                ADDRESS_TEXT_PATTERN,
                "Country name"
        );

        this.zipcode = validateAndNormalize(
                zipcode,
                ZIPCODE_MAX_LENGTH,
                ZIPCODE_PATTERN,
                "Zipcode"
        ).toUpperCase();


        this.city = validateAndNormalize(
                city,
                CITY_MAX_LENGTH,
                ADDRESS_TEXT_PATTERN,
                "City name"
        );

        this.street = validateAndNormalize(
                street,
                STREET_MAX_LENGTH,
                ADDRESS_TEXT_PATTERN,
                "Street name"
        );

        this.building = validateAndNormalize(
                building,
                BUILDING_MAX_LENGTH,
                ADDRESS_TEXT_PATTERN,
                "Building"
        );

        this.apartment = apartment == null || apartment.isBlank() ? null : validateAndNormalize(
                apartment,
                APARTMENT_MAX_LENGTH,
                ADDRESS_TEXT_PATTERN,
                "Apartment"
        );
    }

    private static String validateAndNormalize(String value,
                                               int maxLength,
                                               Pattern pattern,
                                               String fieldName) {
        checkIfNullOrBlank(value, fieldName);
        var normalized = value.trim();
        checkLength(normalized, maxLength, fieldName);
        checkIfMatches(normalized, pattern, fieldName);
        return normalized;
    }

    private static void checkIfNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidAddressException("%s is required".formatted(fieldName));
        }
    }

    private static void checkLength(String value, int maxLength, String fieldName) {
        if (value.length() > maxLength) {
            throw new InvalidAddressException(
                    "%s must not be longer than %d characters, yours is %d"
                            .formatted(fieldName, maxLength, value.length())
            );
        }
    }

    private static void checkIfMatches(String value, Pattern pattern, String fieldName) {
        if (!pattern.matcher(value).matches()) {
            throw new InvalidAddressException(
                    "%s doesn't match the required pattern".formatted(fieldName)
            );
        }
    }
}
