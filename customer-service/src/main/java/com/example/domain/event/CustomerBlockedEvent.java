package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerBlockedEvent implements DomainEvent {

    UUID aggregateId;
    String reason;
    Instant occurredAt;

    @Builder
    private CustomerBlockedEvent(@NonNull UUID aggregateId,
                                 @NonNull String reason) {
        this.aggregateId = aggregateId;
        this.reason = reason;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getType() {
        return "CUSTOMER_BLOCKED";
    }
}
