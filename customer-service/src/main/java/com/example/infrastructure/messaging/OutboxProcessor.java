package com.example.infrastructure.messaging;

import com.example.infrastructure.persistence.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topics.customer}")
    private String customerMonitoringTopic;

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void processOutbox() {
        var newBatch = outboxRepository.findTop100BySentFalseOrderByIdAsc();
        if (newBatch.isEmpty()) {
            log.debug("Outbox is currently empty");
            return;
        }

        var results = new ArrayList<CompletableFuture<SendResult<String, String>>>();
        log.info("Found {} outbox messages", newBatch.size());
        for (var message : newBatch) {
            log.debug("Sending outbox message with id {}", message.getId());
            var sendResult = kafkaTemplate.send(customerMonitoringTopic, message.getAggregateId(), message.getPayload());
            results.add(sendResult);
        }
        for (int i = 0; i < newBatch.size(); i++) {
            try {
                var recordMetadata = results.get(i).get(5, TimeUnit.SECONDS).getRecordMetadata();
                var message = newBatch.get(i);
                message.markAsSent();
                log.info("Sent message with id {} to topic {}, partition {}",
                    message.getId(), recordMetadata.topic(), recordMetadata.partition()
                );
            } catch (Exception e) {
                log.warn("Failed to send message with id {}, exception: {}",
                        newBatch.get(i).getId(), e.getMessage());
                break;
            }
        }
    }
}
