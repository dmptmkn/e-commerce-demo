package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerUnblockedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerUnblockedEventHandler extends DomainEventHandler<CustomerUnblockedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerUnblockedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                       CustomerEventMapper mapper) {
        super(CustomerUnblockedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerUnblockedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.unblockCustomer(dto);
    }
}
