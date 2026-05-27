package com.example.adapter.in.messaging;

import com.example.core.domain.event.DomainEvent;
import com.example.core.port.in.DomainEventProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static com.example.core.domain.event.CustomerEventType.CUSTOMER_ADDRESS_CHANGED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_BLOCKED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_DELETED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_EMAIL_CHANGED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_FULL_NAME_CHANGED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_PHONE_CHANGED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_REGISTERED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_UNBLOCKED;
import static com.example.core.domain.event.CustomerEventType.CUSTOMER_USER_NAME_CHANGED;
import static com.example.core.domain.event.CustomerEventType.LOYALTY_POINTS_ADDED;
import static com.example.core.domain.event.CustomerEventType.LOYALTY_POINTS_SUBTRACTED;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDomainEventHandler {

    private static final Map<String, Class<? extends DomainEvent>> EVENT_TYPE_MAP = Map.ofEntries(
            Map.entry(CUSTOMER_REGISTERED.getEventType(), CUSTOMER_REGISTERED.getEventClass()),
            Map.entry(CUSTOMER_EMAIL_CHANGED.getEventType(), CUSTOMER_EMAIL_CHANGED.getEventClass()),
            Map.entry(CUSTOMER_PHONE_CHANGED.getEventType(), CUSTOMER_PHONE_CHANGED.getEventClass()),
            Map.entry(CUSTOMER_USER_NAME_CHANGED.getEventType(), CUSTOMER_USER_NAME_CHANGED.getEventClass()),
            Map.entry(CUSTOMER_FULL_NAME_CHANGED.getEventType(), CUSTOMER_FULL_NAME_CHANGED.getEventClass()),
            Map.entry(CUSTOMER_ADDRESS_CHANGED.getEventType(), CUSTOMER_ADDRESS_CHANGED.getEventClass()),
            Map.entry(CUSTOMER_BLOCKED.getEventType(), CUSTOMER_BLOCKED.getEventClass()),
            Map.entry(CUSTOMER_UNBLOCKED.getEventType(), CUSTOMER_UNBLOCKED.getEventClass()),
            Map.entry(CUSTOMER_DELETED.getEventType(), CUSTOMER_DELETED.getEventClass()),
            Map.entry(LOYALTY_POINTS_ADDED.getEventType(), LOYALTY_POINTS_ADDED.getEventClass()),
            Map.entry(LOYALTY_POINTS_SUBTRACTED.getEventType(), LOYALTY_POINTS_SUBTRACTED.getEventClass())
    );

    private final DomainEventProcessor eventProcessor;
    private final ObjectMapper jsonMapper;

    @KafkaListener(topics = "${kafka.topics.customer}")
    @RetryableTopic(
            attempts = "4",
            backOff = @BackOff(delay = 1000, multiplier = 2.0),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            dltTopicSuffix = ".DLT"
    )
    public void handleDomainEvent(@Payload String payloadJson,
                                  @Header("type") String eventTypeHeader,
                                  @Header("messageId") String messageIdHeader) {
        var eventClass = EVENT_TYPE_MAP.get(eventTypeHeader);
        if (eventClass != null) {
            try {
                var domainEvent = jsonMapper.readValue(payloadJson, eventClass);
                eventProcessor.handleDomainEvent(domainEvent);
            } catch (Exception e) {
                log.warn("Failed to send message {}", messageIdHeader, e);
                throw new RuntimeException("Processing failed", e);
            }
        } else {
            log.warn("Message {} has unknown event type", messageIdHeader);
        }
    }
}
