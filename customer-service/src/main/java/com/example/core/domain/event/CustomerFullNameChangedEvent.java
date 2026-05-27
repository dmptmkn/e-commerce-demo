package com.example.core.domain.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerFullNameChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldFirstName;
    String newFirstName;
    String oldLastName;
    String newLastName;
    Instant occurredAt;

    @Builder
    @Jacksonized
    private CustomerFullNameChangedEvent(UUID aggregateId,
                                         String oldFirstName,
                                         String newFirstName,
                                         String oldLastName,
                                         String newLastName) {
        this.aggregateId = aggregateId;
        this.oldFirstName = oldFirstName;
        this.newFirstName = newFirstName;
        this.oldLastName = oldLastName;
        this.newLastName = newLastName;
        this.occurredAt = Instant.now();
    }


    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return CustomerEventType.CUSTOMER_FULL_NAME_CHANGED.getEventType();
    }
}
