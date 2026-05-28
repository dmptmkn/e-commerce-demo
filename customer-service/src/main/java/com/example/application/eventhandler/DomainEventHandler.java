package com.example.application.eventhandler;

import com.example.core.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DomainEventHandler<T extends DomainEvent> {

    private final Class<T> eventType;

    public Class<T> supportedEventType() {
        return eventType;
    }

    @SuppressWarnings("unchecked")
    public final void handle(DomainEvent event) {
        doHandle((T) event);
    }

    protected abstract void doHandle(T event);
}
