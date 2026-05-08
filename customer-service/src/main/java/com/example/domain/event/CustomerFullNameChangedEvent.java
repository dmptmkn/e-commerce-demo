package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
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
    private CustomerFullNameChangedEvent(@NonNull UUID aggregateId,
                                         @NonNull String oldName,
                                         @NonNull String newName) {
        this.aggregateId = aggregateId;
        this.oldName = oldName;
        this.newName = newName;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getType() {
        return "CUSTOMER_FULL_NAME_CHANGED";
    }
}
