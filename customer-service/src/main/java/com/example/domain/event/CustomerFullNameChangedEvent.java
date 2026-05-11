package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerFullNameChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldName;
    String newName;
    Instant occurredAt;

    @Builder
    private CustomerFullNameChangedEvent(UUID aggregateId, String oldName, String newName) {
        this.aggregateId = aggregateId;
        this.oldName = oldName;
        this.newName = newName;
        this.occurredAt = Instant.now();
    }


    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return "CUSTOMER_FULL_NAME_CHANGED";
    }
}
