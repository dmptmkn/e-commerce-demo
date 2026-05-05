package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class CustomerEmailChangedEvent implements DomainEvent {

    UUID aggregateId;
    String oldEmail;
    String newEmail;
    Instant occurredAt;

    @Builder
    private CustomerEmailChangedEvent(@NonNull UUID aggregateId,
                                      @NonNull String oldEmail,
                                      @NonNull String newEmail) {
        this.aggregateId = aggregateId;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "CUSTOMER_EMAIL_CHANGED";
    }
}