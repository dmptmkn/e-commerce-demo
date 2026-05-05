package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class CustomerAddressChangedEvent implements DomainEvent {

    UUID aggregateId;
    String country;
    String zipcode;
    String city;
    String street;
    String building;
    String apartment;
    Instant occurredAt;

    @Builder
    private CustomerAddressChangedEvent(@NonNull UUID aggregateId,
                                        @NonNull String country,
                                        @NonNull String zipcode,
                                        @NonNull String city,
                                        @NonNull String street,
                                        @NonNull String building,
                                        String apartment) {
        this.aggregateId = aggregateId;
        this.country = country;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "CUSTOMER_ADDRESS_CHANGED";
    }
}