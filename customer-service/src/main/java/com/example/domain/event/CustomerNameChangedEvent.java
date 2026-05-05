package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class CustomerNameChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldName;
    String newName;
    Instant occurredAt;

    @Builder
    private CustomerNameChangedEvent(@NonNull UUID aggregateId,
                                     @NonNull String oldName,
                                     @NonNull String newName) {
        this.aggregateId = aggregateId;
        this.oldName = oldName;
        this.newName = newName;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "CUSTOMER_NAME_CHANGED";
    }
}
