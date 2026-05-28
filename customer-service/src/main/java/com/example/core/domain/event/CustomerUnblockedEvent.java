package com.example.core.domain.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerUnblockedEvent implements DomainEvent {

    UUID aggregateId;
    String reason;
    Instant occurredAt;

    @Builder
    @Jacksonized
    private CustomerUnblockedEvent(UUID aggregateId, String reason) {
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
        return CustomerEventType.CUSTOMER_UNBLOCKED.getEventType();
    }
}
