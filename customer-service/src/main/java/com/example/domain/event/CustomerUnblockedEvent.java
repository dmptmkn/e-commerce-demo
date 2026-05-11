package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerUnblockedEvent implements DomainEvent {

    UUID aggregateId;
    String reason;
    Instant occurredAt;

    @Builder
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
        return "CUSTOMER_UNBLOCKED";
    }
}
