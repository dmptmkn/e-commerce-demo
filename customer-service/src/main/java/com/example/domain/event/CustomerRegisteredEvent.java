package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class CustomerRegisteredEvent implements DomainEvent {

    UUID aggregateId;
    String email;
    String phone;
    String userName;
    String fullName;
    Instant occurredAt;

    @Builder
    private CustomerRegisteredEvent(@NonNull UUID aggregateId,
                                    @NonNull String email,
                                    @NonNull String phone,
                                    @NonNull String userName,
                                    @NonNull String fullName) {
        this.aggregateId = aggregateId;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.fullName = fullName;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getType() {
        return "CUSTOMER_REGISTERED";
    }
}
