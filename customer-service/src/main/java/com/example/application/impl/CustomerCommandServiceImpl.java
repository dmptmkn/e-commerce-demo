package com.example.application.impl;

import com.example.application.CustomerCommandService;
import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerStatusCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;
import com.example.application.port.DomainEventPublisher;
import com.example.domain.Customer;
import com.example.domain.CustomerId;
import com.example.domain.CustomerRepository;
import com.example.domain.exception.CustomerNameAlreadyExistsException;
import com.example.domain.exception.CustomerNotFoundException;
import com.example.domain.exception.EmailAlreadyExistsException;
import com.example.domain.exception.PhoneNumberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    @Transactional
    public void register(RegisterCustomerCommand command) {
        var email = command.email();
        var phone = command.phone();
        log.debug("Registering new customer with email: {}", email.getValue());

        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        if (repository.existsByPhone(phone)) {
            throw new PhoneNumberAlreadyExistsException(phone);
        }
        var newCustomer = Customer.register(email, phone, command.name(), command.address());

        saveAndPublishEvents(newCustomer);
        log.info("Customer registered and events published: {}", newCustomer.getId().getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void changeEmail(ChangeCustomerEmailCommand command) {
        var customerId = command.id();
        log.debug("Changing email for customer: {}", customerId.getValue());
        var customer = findById(customerId);
        var newEmail = command.newEmail();

        if (repository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException(newEmail);
        }

        customer.changeEmail(newEmail);
        saveAndPublishEvents(customer);
        log.info("Email changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void changePhone(ChangeCustomerPhoneCommand command) {
        var customerId = command.id();
        log.debug("Changing phone for customer: {}", customerId.getValue());
        var customer = findById(customerId);
        var newPhone = command.newPhone();

        if (repository.existsByPhone(newPhone)) {
            throw new PhoneNumberAlreadyExistsException(newPhone);
        }

        customer.changePhoneNumber(newPhone);
        saveAndPublishEvents(customer);
        log.info("Phone changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void changeName(ChangeCustomerNameCommand command) {
        var customerId = command.id();
        log.debug("Changing name for customer: {}", customerId.getValue());
        var customer = findById(customerId);
        var newName = command.newName();

        if (repository.existsByName(newName)) {
            throw new CustomerNameAlreadyExistsException(newName);
        }

        customer.changeName(newName);
        saveAndPublishEvents(customer);
        log.info("Name changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void changeAddress(ChangeCustomerAddressCommand command) {
        var customerId = command.id();
        log.debug("Changing address for customer: {}", customerId.getValue());
        var customer = findById(customerId);

        customer.changeAddress(command.newAddress());
        saveAndPublishEvents(customer);
        log.info("Address changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void changeStatus(ChangeCustomerStatusCommand command) {
        var customerId = command.id();
        var newStatus = command.newStatus();
        log.debug("Changing status for customer: {} to {}", customerId.getValue(), newStatus);
        var customer = findById(customerId);

        customer.changeStatus(newStatus);
        saveAndPublishEvents(customer);
        log.info("Status changed for customer: {} to {}", customerId.getValue(), newStatus);
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void addLoyaltyPoints(AddLoyaltyPointsCommand command) {
        var customerId = command.id();
        var points = command.points().getValue();
        log.debug("Adding loyalty points to customer: {}, points: {}", customerId.getValue(), points);
        var customer = findById(customerId);

        customer.addLoyaltyPoints(points);
        saveAndPublishEvents(customer);
        log.info("Added {} loyalty points to customer: {}", points, customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerInfo", key = "#command.id.value")
    public void subtractLoyaltyPoints(SubtractLoyaltyPointsCommand command) {
        var customerId = command.id();
        var points = command.points().getValue();
        log.debug("Subtracting loyalty points from customer: {}, points: {}", customerId.getValue(), points);
        var customer = findById(customerId);

        customer.subtractLoyaltyPoints(points);
        saveAndPublishEvents(customer);
        log.info("Subtracted {} loyalty points from customer: {}", points, customerId.getValue());
    }

    private Customer findById(CustomerId id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void saveAndPublishEvents(Customer customer) {
        repository.save(customer);
        customer.pullEvents().forEach(eventPublisher::publish);
    }
}
