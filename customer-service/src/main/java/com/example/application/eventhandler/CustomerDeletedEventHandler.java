package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerDeletedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerDeletedEventHandler extends DomainEventHandler<CustomerDeletedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerDeletedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                       CustomerEventMapper mapper) {
        super(CustomerDeletedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerDeletedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.markDeleted(dto);
    }
}
