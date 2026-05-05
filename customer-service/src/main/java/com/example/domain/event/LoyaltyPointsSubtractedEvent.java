package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class LoyaltyPointsSubtractedEvent implements DomainEvent {

    UUID aggregateId;
    Integer pointsSubtracted;
    Integer newBalance;
    Instant occurredAt;

    @Builder
    private LoyaltyPointsSubtractedEvent(@NonNull UUID aggregateId,
                                         @NonNull Integer pointsSubtracted,
                                         @NonNull Integer newBalance) {
        this.aggregateId = aggregateId;
        this.pointsSubtracted = pointsSubtracted;
        this.newBalance = newBalance;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "LOYALTY_POINTS_SUBTRACTED";
    }
}