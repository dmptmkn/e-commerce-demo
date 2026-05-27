package com.example.adapter.out.outbox;

import com.example.core.domain.event.DomainEvent;
import com.example.core.port.out.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper jsonMapper;

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
