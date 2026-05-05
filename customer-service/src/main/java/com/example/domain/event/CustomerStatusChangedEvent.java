package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
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
    public String type() {
        return "CUSTOMER_STATUS_CHANGED";
    }
}