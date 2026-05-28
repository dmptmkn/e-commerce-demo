package com.example.core.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    @JsonIgnore String getAggregateType();
    UUID getAggregateId();
    Instant getOccurredAt();
    @JsonIgnore String getEventType();
}
