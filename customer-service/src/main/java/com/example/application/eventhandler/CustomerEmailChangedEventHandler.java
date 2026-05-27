package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerEmailChangedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class CustomerEmailChangedEventHandler extends DomainEventHandler<CustomerEmailChangedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerEmailChangedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                            CustomerEventMapper mapper) {
        super(CustomerEmailChangedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerEmailChangedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.updateEmail(dto);
    }
}
