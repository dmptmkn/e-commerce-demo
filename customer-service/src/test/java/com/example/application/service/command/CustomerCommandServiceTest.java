package com.example.application.service.command;

import com.example.application.command.BlockCustomerCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.core.domain.aggregate.Customer;
import com.example.core.domain.event.CustomerBlockedEvent;
import com.example.core.domain.event.CustomerRegisteredEvent;
import com.example.core.domain.exception.CustomerNotFoundException;
import com.example.core.domain.exception.EmailAlreadyExistsException;
import com.example.core.domain.exception.PhoneNumberAlreadyExistsException;
import com.example.core.domain.exception.UserNameAlreadyExistsException;
import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.FullName;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.Reason;
import com.example.core.domain.valueobject.UserName;
import com.example.core.port.out.CustomerRepository;
import com.example.core.port.out.DomainEventPublisher;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerCommandServiceTest {

    private final static CustomerId ID = CustomerId.of(UUID.fromString("79289795-31ae-44c5-9fe1-5697e0bbc223"));
    private final static Email EMAIL = Email.of("test@test.com");
    private final static PhoneNumber PHONE_NUMBER = PhoneNumber.of("+78002002316");
    private final static UserName USER_NAME = UserName.of("test");
    private final static FullName FULL_NAME = FullName.of("Test", "Test");
    private final static Address ADDRESS = Address.builder()
            .country("Россия")
            .zipcode("103132")
            .city("Москва")
            .street("Красная площадь")
            .building("7")
            .apartment("1")
            .build();
    private final static RegisterCustomerCommand REGISTER_CUSTOMER_COMMAND = new RegisterCustomerCommand(
            EMAIL,
            PHONE_NUMBER,
            USER_NAME,
            FULL_NAME,
            ADDRESS
    );
    private final static BlockCustomerCommand BLOCK_CUSTOMER_COMMAND = new BlockCustomerCommand(ID, Reason.none());

    @Mock
    private Customer mockCustomer;
    @Mock
    private CustomerRepository repository;
    @Mock
    private DomainEventPublisher eventPublisher;
    @InjectMocks
    private CustomerCommandService service;

    @Nested
    class RegistrationTests {

        @Test
        void shouldRegister_whenAllDataIsUnique() {
            when(repository.existsByEmail(EMAIL)).thenReturn(false);
            when(repository.existsByPhone(PHONE_NUMBER)).thenReturn(false);
            when(repository.existsByUserName(USER_NAME)).thenReturn(false);

            service.register(REGISTER_CUSTOMER_COMMAND);

            verify(repository).existsByEmail(EMAIL);
            verify(repository).existsByPhone(PHONE_NUMBER);
            verify(repository).existsByUserName(USER_NAME);
            var customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
            verify(repository).save(customerArgumentCaptor.capture());
            var savedCustomer = customerArgumentCaptor.getValue();
            assertThat(savedCustomer)
                    .extracting(
                            Customer::getEmail,
                            Customer::getPhone,
                            Customer::getUserName,
                            Customer::getFullName,
                            Customer::getAddress
                    )
                    .containsExactly(
                            EMAIL,
                            PHONE_NUMBER,
                            USER_NAME,
                            FULL_NAME,
                            ADDRESS
                    );
            var eventArgumentCaptor = ArgumentCaptor.forClass(CustomerRegisteredEvent.class);
            verify(eventPublisher).publish(eventArgumentCaptor.capture());
            var publishedEvent = eventArgumentCaptor.getValue();
            assertThat(publishedEvent)
                    .extracting(
                            CustomerRegisteredEvent::getEmail,
                            CustomerRegisteredEvent::getPhone,
                            CustomerRegisteredEvent::getUserName,
                            CustomerRegisteredEvent::getFirstName,
                            CustomerRegisteredEvent::getLastName,
                            CustomerRegisteredEvent::getCountry,
                            CustomerRegisteredEvent::getZipcode,
                            CustomerRegisteredEvent::getCity,
                            CustomerRegisteredEvent::getStreet,
                            CustomerRegisteredEvent::getBuilding,
                            CustomerRegisteredEvent::getApartment
                    )
                    .containsExactly(
                            EMAIL.getValue(),
                            PHONE_NUMBER.getValue(),
                            USER_NAME.getValue(),
                            FULL_NAME.getFirstName(),
                            FULL_NAME.getLastName(),
                            ADDRESS.getCountry(),
                            ADDRESS.getZipcode(),
                            ADDRESS.getCity(),
                            ADDRESS.getStreet(),
                            ADDRESS.getBuilding(),
                            ADDRESS.getApartment()
                    );
        }

        @Test
        void shouldThrowException_whenEmailIsAlreadyTaken() {
            when(repository.existsByEmail(EMAIL)).thenReturn(true);

            assertThatThrownBy(() -> service.register(REGISTER_CUSTOMER_COMMAND))
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessage("Email %s is already taken".formatted(EMAIL.getValue()));
            verify(repository).existsByEmail(EMAIL);
            verify(repository, never()).save(any(Customer.class));
            verify(eventPublisher, never()).publish(any(CustomerRegisteredEvent.class));
        }

        @Test
        void shouldThrowException_whenPhoneNumberIsAlreadyTaken() {
            when(repository.existsByPhone(PHONE_NUMBER)).thenReturn(true);

            assertThatThrownBy(() -> service.register(REGISTER_CUSTOMER_COMMAND))
                    .isInstanceOf(PhoneNumberAlreadyExistsException.class)
                    .hasMessage("Phone number %s is already taken".formatted(PHONE_NUMBER.getValue()));
            verify(repository).existsByPhone(PHONE_NUMBER);
            verify(repository, never()).save(any(Customer.class));
            verify(eventPublisher, never()).publish(any(CustomerRegisteredEvent.class));
        }

        @Test
        void shouldThrowException_whenUserNameIsAlreadyTaken() {
            when(repository.existsByUserName(USER_NAME)).thenReturn(true);

            assertThatThrownBy(() -> service.register(REGISTER_CUSTOMER_COMMAND))
                    .isInstanceOf(UserNameAlreadyExistsException.class)
                    .hasMessage("Customer with user name %s already exists".formatted(USER_NAME.getValue()));
            verify(repository).existsByUserName(USER_NAME);
            verify(repository, never()).save(any(Customer.class));
            verify(eventPublisher, never()).publish(any(CustomerRegisteredEvent.class));
        }
    }

    @Nested
    class BlockingTest {

        @Test
        void shouldBlock_whenAllDataIsValid() {
            when(repository.findById(ID)).thenReturn(Optional.of(mockCustomer));
            var expectedEvent = CustomerBlockedEvent.builder()
                    .aggregateId(ID.getValue())
                    .reason(BLOCK_CUSTOMER_COMMAND.reason().getValue())
                    .build();
            when(mockCustomer.pullEvents()).thenReturn(List.of(expectedEvent));

            service.block(BLOCK_CUSTOMER_COMMAND);

            verify(repository).findById(ID);
            verify(mockCustomer).block(BLOCK_CUSTOMER_COMMAND.reason());
            verify(repository).save(mockCustomer);
            verify(mockCustomer).pullEvents();
            var eventArgumentCaptor = ArgumentCaptor.forClass(CustomerBlockedEvent.class);
            verify(eventPublisher).publish(eventArgumentCaptor.capture());
            var publishedEvent = eventArgumentCaptor.getValue();
            assertThat(publishedEvent)
                    .extracting(CustomerBlockedEvent::getAggregateId, CustomerBlockedEvent::getReason)
                    .containsExactly(ID.getValue(), BLOCK_CUSTOMER_COMMAND.reason().getValue());
        }

        @Test
        void shouldThrowException_whenCustomerNotFound() {
            when(repository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.block(BLOCK_CUSTOMER_COMMAND))
                    .isInstanceOf(CustomerNotFoundException.class)
                    .hasMessage("Customer with id %s not found".formatted(ID.getValue()));
            verify(repository).findById(ID);
            verify(repository, never()).save(any(Customer.class));
            verify(eventPublisher, never()).publish(any(CustomerBlockedEvent.class));
        }
    }
}
