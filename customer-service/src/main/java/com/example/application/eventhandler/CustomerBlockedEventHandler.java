package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerBlockedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerBlockedEventHandler extends DomainEventHandler<CustomerBlockedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerBlockedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                       CustomerEventMapper mapper) {
        super(CustomerBlockedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerBlockedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.blockCustomer(dto);
    }
}
