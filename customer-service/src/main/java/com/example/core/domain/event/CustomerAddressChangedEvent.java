package com.example.core.domain.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
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
    @Jacksonized
    private CustomerAddressChangedEvent(UUID aggregateId,
                                        String country,
                                        String zipcode,
                                        String city,
                                        String street,
                                        String building,
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
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return CustomerEventType.CUSTOMER_ADDRESS_CHANGED.getEventType();
    }
}
