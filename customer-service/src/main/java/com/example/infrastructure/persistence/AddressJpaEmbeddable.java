package com.example.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressJpaEmbeddable {

    @Column(name = "address_country", nullable = false)
    private String country;

    @Column(name = "address_zipcode", nullable = false)
    private String zipcode;

    @Column(name = "address_city", nullable = false)
    private String city;

    @Column(name = "address_street", nullable = false)
    private String street;

    @Column(name = "address_building", nullable = false)
    private String building;

    @Column(name = "address_apartment")
    private String apartment;
}
