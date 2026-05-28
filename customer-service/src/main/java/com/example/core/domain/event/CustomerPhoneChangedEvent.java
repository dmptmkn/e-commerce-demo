package com.example.core.domain.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerPhoneChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldNumber;
    String newNumber;
    Instant occurredAt;

    @Builder
    @Jacksonized
    private CustomerPhoneChangedEvent(UUID aggregateId, String oldNumber, String newNumber) {
        this.aggregateId = aggregateId;
        this.oldNumber = oldNumber;
        this.newNumber = newNumber;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return CustomerEventType.CUSTOMER_PHONE_CHANGED.getEventType();
    }
}
