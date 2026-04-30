package com.example.infrastructure.persistence;

import com.example.infrastructure.messaging.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
@Getter
public class OutboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "outbox_id_gen")
    @SequenceGenerator(name = "outbox_id_gen", sequenceName = "outbox_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;
    @Column(name = "event_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EventType eventType;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private boolean sent;

    @Builder
    private OutboxMessage(String aggregateId, EventType eventType, String payload) {
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.sent = false;
    }

    public void markAsSent() {
        this.sent = true;
    }
}