package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerFullNameChangedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerFullNameChangedEventHandler extends DomainEventHandler<CustomerFullNameChangedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerFullNameChangedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                               CustomerEventMapper mapper) {
        super(CustomerFullNameChangedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerFullNameChangedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.updateFullName(dto);
    }
}
