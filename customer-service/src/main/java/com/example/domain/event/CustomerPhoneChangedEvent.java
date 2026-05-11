package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerPhoneChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldNumber;
    String newNumber;
    Instant occurredAt;

    @Builder
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
        return "CUSTOMER_PHONE_CHANGED";
    }
}
