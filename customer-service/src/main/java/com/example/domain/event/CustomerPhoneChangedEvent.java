package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerPhoneChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldNumber;
    String newNumber;
    Instant occurredAt;

    @Builder
    private CustomerPhoneChangedEvent(@NonNull UUID aggregateId,
                                      @NonNull String oldNumber,
                                      @NonNull String newNumber) {
        this.aggregateId = aggregateId;
        this.oldNumber = oldNumber;
        this.newNumber = newNumber;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getType() {
        return "CUSTOMER_PHONE_CHANGED";
    }
}
