package com.example.application.impl;

import com.example.application.CustomerService;
import com.example.application.command.RegisterCustomerCommand;
import com.example.domain.Address;
import com.example.domain.Customer;
import com.example.domain.Email;
import com.example.domain.PhoneNumber;
import com.example.infrastructure.mapper.CustomerPersistenceMapper;
import com.example.infrastructure.messaging.EventType;
import com.example.infrastructure.persistence.OutboxMessage;
import com.example.infrastructure.persistence.repository.CustomerJpaRepository;
import com.example.infrastructure.persistence.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerJpaRepository jpaRepository;
    private final OutboxRepository outboxRepository;
    private final CustomerPersistenceMapper persistenceMapper;
    private final ObjectMapper jsonMapper;

    @Override
    @Transactional
    public void register(RegisterCustomerCommand command) {
        var newCustomer = Customer.register(
                Email.of(command.email()),
                PhoneNumber.of(command.phone()),
                command.name(),
                Address.of(
                        command.country(),
                        command.zipcode(),
                        command.city(),
                        command.street(),
                        command.building(),
                        command.apartment()
                )
        );
        log.info("Registering a new customer: {}", newCustomer);

        var customerJpaEntity = persistenceMapper.toJpaEntity(newCustomer);
        var newCustomerJson = jsonMapper.writeValueAsString(newCustomer);

        jpaRepository.save(customerJpaEntity);
        outboxRepository.save(
                OutboxMessage.builder()
                        .aggregateId(newCustomer.getId().toString())
                        .eventType(EventType.CUSTOMER_REGISTERED)
                        .payload(newCustomerJson)
                        .build()
        );
        log.info("Customer registered: {}", newCustomer.getId());
    }
}