package com.example.application.eventhandler;

import com.example.core.domain.event.DomainEvent;
import com.example.core.port.in.DomainEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DomainEventDispatcher implements DomainEventProcessor {

    private final Map<Class<? extends DomainEvent>, DomainEventHandler<? extends DomainEvent>> eventHandlerMap;

    @Autowired
    public DomainEventDispatcher(List<DomainEventHandler<? extends DomainEvent>> handlers) {
        this.eventHandlerMap = handlers.stream()
                .collect(Collectors.toMap(
                        DomainEventHandler::supportedEventType,
                        Function.identity())
                );
    }

    @Override
    public void handleDomainEvent(DomainEvent event) {
        var handler = eventHandlerMap.get(event.getClass());
        if (handler != null) {
            handler.handle(event);
        } else {
            log.warn("Cannot find handler for domain event {} type", event.getEventType());
        }
    }
}
