package com.example.infrastructure.messaging.converter;

import com.example.application.port.DomainEventPublisher;
import com.example.domain.event.DomainEvent;
import com.example.infrastructure.persistence.OutboxMessage;
import com.example.infrastructure.persistence.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper jsonMapper;

    @Transactional
    public void publish(DomainEvent event) {
        outboxRepository.save(
                OutboxMessage.builder()
                        .aggregateType(event.getAggregateType())
                        .aggregateId(event.getAggregateId().toString())
                        .eventType(event.getEventType())
                        .payload(jsonMapper.writeValueAsString(event))
                        .build()
        );
        log.debug("Domain event {} saved to outbox for aggregate {} with id {}",
                event.getEventType(), event.getAggregateType(), event.getAggregateId());
    }
}
