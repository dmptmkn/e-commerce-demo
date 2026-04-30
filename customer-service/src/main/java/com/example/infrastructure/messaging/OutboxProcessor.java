package com.example.infrastructure.messaging;

import com.example.infrastructure.persistence.OutboxMessage;
import com.example.infrastructure.persistence.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper jsonMapper;
    @Value("${kafka.topics.customer}")
    private String customerMonitoringTopic;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        var newBatch = outboxRepository.findTop100BySentFalseOrderByIdAsc();
        if (newBatch.isEmpty()) {
            log.debug("Outbox is currently empty");
            return;
        }

        log.info("Found {} outbox messages", newBatch.size());
        for (var message : newBatch) {
            log.debug("Sending outbox message id={}", message.getId());
            try {
                var payload = jsonMapper.writeValueAsString(message);
                kafkaTemplate
                        .send(customerMonitoringTopic, message.getAggregateId(), payload)
                        .get(10, TimeUnit.SECONDS);
                message.markAsSent();
                log.debug("Sent outbox message id={}, eventType={}", message.getId(), message.getEventType());
            } catch (Exception e) {
                log.error("Failed to send message id={}, will retry later", message.getId(), e);
                break;
            }
        }
    }
}