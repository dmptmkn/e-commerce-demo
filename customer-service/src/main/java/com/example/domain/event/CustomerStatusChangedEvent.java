package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerStatusChangedEvent implements DomainEvent {

    UUID aggregateId;
    String newStatus;
    Instant occurredAt;

    @Builder
    private CustomerStatusChangedEvent(@NonNull UUID aggregateId,
                                       @NonNull String newStatus) {
        this.aggregateId = aggregateId;
        this.newStatus = newStatus;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getType() {
        return "CUSTOMER_STATUS_CHANGED";
    }
}