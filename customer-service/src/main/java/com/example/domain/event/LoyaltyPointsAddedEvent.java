package com.example.domain.event;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Value
@Accessors(fluent = true)
public class LoyaltyPointsAddedEvent implements DomainEvent {

    UUID aggregateId;
    Integer pointsAdded;
    Integer newBalance;
    Instant occurredAt;

    @Builder
    private LoyaltyPointsAddedEvent(@NonNull UUID aggregateId,
                                    @NonNull Integer pointsAdded,
                                    @NonNull Integer newBalance) {
        this.aggregateId = aggregateId;
        this.pointsAdded = pointsAdded;
        this.newBalance = newBalance;
        this.occurredAt = Instant.now();
    }

    @Override
    public String type() {
        return "LOYALTY_POINTS_ADDED";
    }
}