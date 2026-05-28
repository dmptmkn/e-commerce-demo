package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerUserNameChangedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerUserNameChangedEventHandler extends DomainEventHandler<CustomerUserNameChangedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerUserNameChangedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                               CustomerEventMapper mapper) {
        super(CustomerUserNameChangedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerUserNameChangedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.updateUserName(dto);
    }
}
