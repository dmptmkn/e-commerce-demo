package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerRegisteredEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRegisteredEventHandler extends DomainEventHandler<CustomerRegisteredEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerRegisteredEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                          CustomerEventMapper mapper) {
        super(CustomerRegisteredEvent.class);
        this.mapper = mapper;
        this.readModelCommandPort = readModelCommandPort;
    }

    @Override
    protected void doHandle(CustomerRegisteredEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.createCustomer(dto);
    }
}
