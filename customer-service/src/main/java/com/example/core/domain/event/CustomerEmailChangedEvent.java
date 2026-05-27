package com.example.core.domain.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerEmailChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldEmail;
    String newEmail;
    Instant occurredAt;

    @Builder
    @Jacksonized
    private CustomerEmailChangedEvent(UUID aggregateId, String oldEmail, String newEmail) {
        this.aggregateId = aggregateId;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return CustomerEventType.CUSTOMER_EMAIL_CHANGED.getEventType();
    }
}
