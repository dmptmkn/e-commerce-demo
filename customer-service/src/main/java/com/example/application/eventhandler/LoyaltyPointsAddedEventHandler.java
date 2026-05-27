package com.example.application.eventhandler;

import com.example.application.mapper.CustomerEventMapper;
import com.example.core.domain.event.LoyaltyPointsAddedEvent;
import com.example.core.port.out.CustomerReadModelCommandPort;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyPointsAddedEventHandler extends DomainEventHandler<LoyaltyPointsAddedEvent> {

    private final CustomerReadModelCommandPort readModelCommandPort;
    private final CustomerEventMapper mapper;

    public LoyaltyPointsAddedEventHandler(CustomerReadModelCommandPort readModelCommandPort, CustomerEventMapper mapper) {
        super(LoyaltyPointsAddedEvent.class);
        this.readModelCommandPort = readModelCommandPort;
        this.mapper = mapper;
    }

    @Override
    protected void doHandle(LoyaltyPointsAddedEvent event) {
        var dto = mapper.toDto(event);
        readModelCommandPort.addLoyaltyPoints(dto);
    }
}
