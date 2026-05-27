package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.CustomerPhoneChangedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerPhoneChangedEventHandler extends DomainEventHandler<CustomerPhoneChangedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    @Autowired
    public CustomerPhoneChangedEventHandler(CustomerReadModelCommandPort readModelCommandPort,
                                            CustomerEventMapper mapper) {
        super(CustomerPhoneChangedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(CustomerPhoneChangedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.updatePhone(dto);
    }
}
