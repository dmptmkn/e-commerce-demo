package com.example.domain.event;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class LoyaltyPointsSubtractedEvent implements DomainEvent {

    UUID aggregateId;
    Integer pointsSubtracted;
    Integer newBalance;
    Instant occurredAt;

    @Builder
    private LoyaltyPointsSubtractedEvent(UUID aggregateId, Integer pointsSubtracted, Integer newBalance) {
        this.aggregateId = aggregateId;
        this.pointsSubtracted = pointsSubtracted;
        this.newBalance = newBalance;
        this.occurredAt = Instant.now();
    }

    @Override
    public String getAggregateType() {
        return "Customer";
    }

    @Override
    public String getEventType() {
        return "LOYALTY_POINTS_SUBTRACTED";
    }
}
