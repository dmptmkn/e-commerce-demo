package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class LoyaltyPointsAddedEvent implements DomainEvent {

    UUID aggregateId;
    Integer pointsAdded;
    Integer newBalance;
    Instant occurredAt;

    @Builder
    private LoyaltyPointsAddedEvent(UUID aggregateId, Integer pointsAdded, Integer newBalance) {
        this.aggregateId = aggregateId;
        this.pointsAdded = pointsAdded;
        this.newBalance = newBalance;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }


    @Override
    public String getEventType() {
        return "LOYALTY_POINTS_ADDED";
    }
}
