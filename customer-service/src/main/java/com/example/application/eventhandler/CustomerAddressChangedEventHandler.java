package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerAddressChangedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressChangedEventHandler extends DomainEventHandler<CustomerAddressChangedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerAddressChangedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                              CustomerEventMapper mapper) {
        super(CustomerAddressChangedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerAddressChangedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.updateAddress(dto);
    }
}
