package com.example.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
@Getter
public class OutboxMessage {

    @Id
    @UuidGenerator
    private UUID id;
    @Column(name = "aggregatetype", nullable = false, length = 100)
    String aggregateType;
    @Column(name = "aggregateid", nullable = false)
    private String aggregateId;
    @Column(name = "type", nullable = false)
    private String eventType;
    @Column(name = "payload", columnDefinition = "TEXT", nullable = false)
    private String payload;
    @CreationTimestamp
    @Column(name = "timestamp", updatable = false)
    private Instant createdAt;
    private boolean sent;

    @Builder
    private OutboxMessage(String aggregateType,
                          String aggregateId,
                          String eventType,
                          String payload) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.sent = false;
    }

    public void markAsSent() {
        this.sent = true;
    }
}
