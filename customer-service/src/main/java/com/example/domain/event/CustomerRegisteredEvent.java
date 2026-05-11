package com.example.domain.event;

import lombok.Builder;
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
    private CustomerRegisteredEvent(UUID aggregateId,
                                    String email,
                                    String phone,
                                    String userName,
                                    String fullName) {
        this.aggregateId = aggregateId;
        this.email = email;
        this.phone = phone;
        this.userName = userName;
        this.fullName = fullName;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return "CUSTOMER_REGISTERED";
    }
}
