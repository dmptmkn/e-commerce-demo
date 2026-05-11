package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerDeletedEvent implements DomainEvent {

    UUID aggregateId;
    String reason;
    Instant occurredAt;

    @Builder
    private CustomerDeletedEvent(UUID aggregateId, String reason) {
        this.aggregateId = aggregateId;
        this.reason = reason;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return "CUSTOMER_DELETED";
    }
}
