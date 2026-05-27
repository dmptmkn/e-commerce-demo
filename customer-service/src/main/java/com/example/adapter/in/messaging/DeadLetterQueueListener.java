package com.example.adapter.in.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadLetterQueueListener {

    @KafkaListener(topics = "${kafka.topics.customer}.DLT", groupId = "${spring.kafka.consumer.group-id}-dlt")
    public void handleDeadLetter(@Payload String message,
                                 @Header("type") String eventType,
                                 @Header("messageId") String messageId,
                                 @Header(value = "x-original-topic", required = false) String originalTopic) {
        log.error("DEAD LETTER - Event type: {}, messageId: {}, original topic: {}, payload: {}",
                eventType, messageId, originalTopic, message);
    }
}
