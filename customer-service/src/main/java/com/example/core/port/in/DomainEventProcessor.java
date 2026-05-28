package com.example.core.port.in;

import com.example.core.domain.event.DomainEvent;

public interface DomainEventProcessor {
    void handleDomainEvent(DomainEvent event);
}
