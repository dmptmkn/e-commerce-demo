package com.example.core.port.out;

import com.example.core.domain.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
