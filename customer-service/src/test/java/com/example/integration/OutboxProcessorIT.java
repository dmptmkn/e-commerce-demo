package com.example.integration;

import com.example.adapter.out.outbox.OutboxMessage;
import com.example.adapter.out.outbox.OutboxProcessor;
import com.example.adapter.out.outbox.OutboxRepository;
import com.example.core.domain.event.CustomerRegisteredEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;
import static org.testcontainers.utility.DockerImageName.parse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@Transactional
class OutboxProcessorIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18.3")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    @Container
    static KafkaContainer kafka = new KafkaContainer(parse("apache/kafka:3.7.0")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
        propertyRegistry.add("spring.liquibase.enabled",
                () -> "true");
        propertyRegistry.add("spring.liquibase.change-log",
                () -> "classpath:db/changelog/db.changelog-master.yaml");
        propertyRegistry.add("spring.jpa.hibernate.ddl-auto",
                () -> "none");
        propertyRegistry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        propertyRegistry.add("kafka.topics.customer", () -> TOPIC);
    }

    private final static UUID ID = UUID.randomUUID();
    private final static String EMAIL = "test@test.com";
    private final static String PHONE_NUMBER = "+78002002316";
    private final static String USER_NAME = "test";
    private final static String FIRST_NAME = "Test";
    private final static String LAST_NAME = "Test";
    private final static String COUNTRY = "Россия";
    private final static String ZIPCODE = "103132";
    private final static String CITY = "Москва";
    private final static String STREET = "Красная площадь";
    private final static String BUILDING = "7";
    private final static String APARTMENT = "1";
    private static final String TOPIC = "customer_monitoring";

    @Autowired
    private OutboxRepository outboxRepository;
    @Autowired
    private OutboxProcessor outboxProcessor;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldProcessOutboxAndSendMessageToKafka() {
        var event = createTestCustomerRegisteredEvent();
        var jsonPayload = objectMapper.writeValueAsString(event);
        var outboxMessage = OutboxMessage.builder()
                .aggregateType(event.getAggregateType())
                .aggregateId(event.getAggregateId().toString())
                .eventType(event.getEventType())
                .payload(jsonPayload)
                .build();
        outboxRepository.save(outboxMessage);
        var consumerProps = KafkaTestUtils.consumerProps(
                kafka.getBootstrapServers(), "test-group", false
        );
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("auto.offset.reset", "earliest");
        var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(consumerProps);
        var consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of(TOPIC));

        outboxProcessor.processOutbox();

        await().atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    var records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(2));
                    assertThat(records.count()).isGreaterThan(0);
                    var record = records.iterator().next();
                    assertThat(record.value()).isEqualTo(jsonPayload);
                });
        var outboxEntry = outboxRepository.findById(outboxMessage.getId()).orElseThrow();
        assertThat(outboxEntry.isSent()).isTrue();

        consumer.close();
    }

    private CustomerRegisteredEvent createTestCustomerRegisteredEvent() {
        return CustomerRegisteredEvent.builder()
                .aggregateId(ID)
                .email(EMAIL)
                .phone(PHONE_NUMBER)
                .userName(USER_NAME)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .country(COUNTRY)
                .zipcode(ZIPCODE)
                .city(CITY)
                .street(STREET)
                .building(BUILDING)
                .apartment(APARTMENT)
                .build();
    }
}