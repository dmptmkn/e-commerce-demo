package com.example.core.domain.valueobject;

import com.example.core.domain.exception.InvalidAddressException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    private static final String VALID_COUNTRY = "Россия";
    private static final String VALID_ZIPCODE = "103132";
    private static final String VALID_CITY = "Москва";
    private static final String VALID_STREET = "Красная площадь";
    private static final String VALID_BUILDING = "7";
    private static final String VALID_APARTMENT = "1";

    private static Address.AddressBuilder defaultBuilder() {
        return Address.builder()
                .zipcode(VALID_ZIPCODE)
                .country(VALID_COUNTRY)
                .city(VALID_CITY)
                .street(VALID_STREET)
                .building(VALID_BUILDING)
                .apartment(VALID_APARTMENT)
                .building(VALID_BUILDING);
    }

    @Test
    void shouldCreate_whenAllFieldValid() {
        var address = defaultBuilder().apartment(VALID_APARTMENT).build();

        assertThat(address)
                .extracting(
                        Address::getCountry, Address::getZipcode, Address::getCity,
                        Address::getStreet, Address::getBuilding, Address::getApartment
                )
                .containsExactly(
                        VALID_COUNTRY, VALID_ZIPCODE, VALID_CITY,
                        VALID_STREET, VALID_BUILDING, VALID_APARTMENT
                );
    }

    @Test
    void shouldCreate_whenAllFieldsValidAndApartmentIsNull() {
        var address = defaultBuilder().apartment(null).build();

        assertThat(address)
                .extracting(
                        Address::getCountry, Address::getZipcode, Address::getCity,
                        Address::getStreet, Address::getBuilding, Address::getApartment
                )
                .containsExactly(
                        VALID_COUNTRY, VALID_ZIPCODE, VALID_CITY,
                        VALID_STREET, VALID_BUILDING, null
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_whenCountryIsBlankOrNull(String invalidCountry) {
        assertThatThrownBy(() -> defaultBuilder().country(invalidCountry).build())
                .isInstanceOf(InvalidAddressException.class)
                .hasMessage("Country name is required");
    }

    @Test
    void shouldThrowException_whenCountryNameIsTooLong() {
        var tooLongCountryName = "A".repeat(101);
        assertThatThrownBy(() -> defaultBuilder().country(tooLongCountryName).build())
                .isInstanceOf(InvalidAddressException.class)
                .hasMessageContaining("Country name must not be longer than 100 characters");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_whenZipcodeIsBlankOrNull(String invalidZipcode) {
        assertThatThrownBy(() -> defaultBuilder().zipcode(invalidZipcode).build())
                .isInstanceOf(InvalidAddressException.class)
                .hasMessage("Zipcode is required");

    }
}