package com.example.application.port;

import com.example.domain.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
