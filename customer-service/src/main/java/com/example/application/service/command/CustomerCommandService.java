package com.example.application.service.command;

import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.BlockCustomerCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerFullNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerUserNameCommand;
import com.example.application.command.DeleteCustomerCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;
import com.example.application.command.UnblockCustomerCommand;
import com.example.core.domain.aggregate.Customer;
import com.example.core.domain.exception.CustomerNotFoundException;
import com.example.core.domain.exception.EmailAlreadyExistsException;
import com.example.core.domain.exception.PhoneNumberAlreadyExistsException;
import com.example.core.domain.exception.UserNameAlreadyExistsException;
import com.example.core.domain.valueobject.CustomerId;
import com.example.core.port.in.CustomerCommandPort;
import com.example.core.port.out.CustomerRepository;
import com.example.core.port.out.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCommandService implements CustomerCommandPort {

    private final CustomerRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", allEntries = true)
    public void register(RegisterCustomerCommand command) {
        var email = command.email();
        var phone = command.phone();
        var userName = command.userName();
        log.debug("Registering new customer with email: {}", email.getValue());

        if (repository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        if (repository.existsByPhone(phone)) {
            throw new PhoneNumberAlreadyExistsException(phone);
        }
        if (repository.existsByUserName(userName)) {
            throw new UserNameAlreadyExistsException(userName);
        }
        var newCustomer = Customer.register(
                email,
                phone,
                userName,
                command.fullName(),
                command.address()
        );

        saveAndPublishEvents(newCustomer);
        log.info("Customer {} registered, id: {}",
                userName.getValue(), newCustomer.getId().getValue()
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void block(BlockCustomerCommand command) {
        var customerId = command.id();
        var customer = findById(command.id());
        log.debug("Blocking customer: {}", customerId.getValue());

        customer.block(command.reason());
        saveAndPublishEvents(customer);
        log.info("Customer blocked: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void unblock(UnblockCustomerCommand command) {
        var customerId = command.id();
        var customer = findById(customerId);
        log.debug("Unblocking customer: {}", customerId.getValue());

        customer.unblock(command.reason());
        saveAndPublishEvents(customer);
        log.info("Customer unblocked: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void delete(DeleteCustomerCommand command) {
        var customerId = command.id();
        var customer = findById(customerId);
        log.debug("Deleting customer: {}", customerId.getValue());

        customer.softDelete(command.reason());
        saveAndPublishEvents(customer);
        log.info("Customer deleted: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void changeEmail(ChangeCustomerEmailCommand command) {
        var customerId = command.id();
        var newEmail = command.newEmail();
        var customer = findById(customerId);
        log.debug("Changing email for customer: {}", customerId.getValue());

        if (repository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException(newEmail);
        }

        customer.changeEmail(newEmail);
        saveAndPublishEvents(customer);
        log.info("Email changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void changePhone(ChangeCustomerPhoneCommand command) {
        var customerId = command.id();
        var newPhone = command.newPhone();
        var customer = findById(customerId);
        log.debug("Changing phone for customer: {}", customerId.getValue());

        if (repository.existsByPhone(newPhone)) {
            throw new PhoneNumberAlreadyExistsException(newPhone);
        }

        customer.changePhoneNumber(newPhone);
        saveAndPublishEvents(customer);
        log.info("Phone changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void changeUserName(ChangeCustomerUserNameCommand command) {
        var customerId = command.id();
        var newName = command.newName();
        var customer = findById(customerId);
        log.debug("Changing user name for customer: {}", customerId.getValue());

        if (repository.existsByUserName(newName)) {
            throw new UserNameAlreadyExistsException(newName);
        }

        customer.changeUserName(newName);
        saveAndPublishEvents(customer);
        log.info("User name changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void changeFullName(ChangeCustomerFullNameCommand command) {
        var customerId = command.id();
        var customer = findById(customerId);
        log.debug("Changing name for customer: {}", customerId.getValue());

        customer.changeFullName(command.newName());
        saveAndPublishEvents(customer);
        log.info("Name changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void changeAddress(ChangeCustomerAddressCommand command) {
        var customerId = command.id();
        var customer = findById(customerId);
        log.debug("Changing address for customer: {}", customerId.getValue());

        customer.changeAddress(command.newAddress());
        saveAndPublishEvents(customer);
        log.info("Address changed for customer: {}", customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void addLoyaltyPoints(AddLoyaltyPointsCommand command) {
        var customerId = command.id();
        var points = command.points().getValue();
        var customer = findById(customerId);
        log.debug("Adding loyalty points to customer: {}, points: {}", customerId.getValue(), points);

        customer.addLoyaltyPoints(points);
        saveAndPublishEvents(customer);
        log.info("Added {} loyalty points to customer: {}", points, customerId.getValue());
    }

    @Override
    @Transactional
    @CacheEvict(value = "customerReadModel", key = "#command.id().getValue()")
    public void subtractLoyaltyPoints(SubtractLoyaltyPointsCommand command) {
        var customerId = command.id();
        var points = command.points().getValue();
        var customer = findById(customerId);
        log.debug("Subtracting loyalty points from customer: {}, points: {}", customerId.getValue(), points);

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
