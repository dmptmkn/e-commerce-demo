package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class CustomerRegisteredEvent implements DomainEvent {

    UUID aggregateId;
    String email;
    String phone;
    String name;
    Instant occurredAt;

    @Builder
    private CustomerRegisteredEvent(@NonNull UUID aggregateId,
                                    @NonNull String email,
                                    @NonNull String phone,
                                    @NonNull String name) {
        this.aggregateId = aggregateId;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "CUSTOMER_REGISTERED";
    }
}
