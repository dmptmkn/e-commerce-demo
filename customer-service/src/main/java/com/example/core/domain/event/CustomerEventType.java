package com.example.core.domain.event;

import lombok.Getter;

@Getter
public enum CustomerEventType {

    CUSTOMER_REGISTERED("CUSTOMER_REGISTERED", CustomerRegisteredEvent.class),
    CUSTOMER_EMAIL_CHANGED("CUSTOMER_EMAIL_CHANGED", CustomerEmailChangedEvent.class),
    CUSTOMER_PHONE_CHANGED("CUSTOMER_PHONE_CHANGED", CustomerPhoneChangedEvent.class),
    CUSTOMER_FULL_NAME_CHANGED("CUSTOMER_FULL_NAME_CHANGED", CustomerFullNameChangedEvent.class),
    CUSTOMER_USER_NAME_CHANGED("CUSTOMER_USER_NAME_CHANGED", CustomerUserNameChangedEvent.class),
    CUSTOMER_ADDRESS_CHANGED("CUSTOMER_ADDRESS_CHANGED", CustomerAddressChangedEvent.class),
    CUSTOMER_BLOCKED("CUSTOMER_BLOCKED", CustomerBlockedEvent.class),
    CUSTOMER_UNBLOCKED("CUSTOMER_UNBLOCKED", CustomerUnblockedEvent.class),
    CUSTOMER_DELETED("CUSTOMER_DELETED", CustomerDeletedEvent.class),
    LOYALTY_POINTS_ADDED("LOYALTY_POINTS_ADDED", LoyaltyPointsAddedEvent.class),
    LOYALTY_POINTS_SUBTRACTED("LOYALTY_POINTS_SUBTRACTED", LoyaltyPointsSubtractedEvent.class);

    CustomerEventType(String eventType, Class<? extends DomainEvent> eventClass) {
        this.eventType = eventType;
        this.eventClass = eventClass;
    }

    private final String eventType;
    private final Class<? extends DomainEvent> eventClass;
}
