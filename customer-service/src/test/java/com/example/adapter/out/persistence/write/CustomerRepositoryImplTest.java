package com.example.adapter.out.persistence.write;

import com.example.adapter.out.persistence.write.mapper.CustomerPersistenceMapper;
import com.example.core.domain.aggregate.Customer;
import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.FullName;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryImplTest {

    @Mock
    private CustomerCommandRepository commandRepository;
    @Mock
    private CustomerPersistenceMapper persistenceMapper;
    @InjectMocks
    private CustomerRepositoryImpl repository;

    private final static CustomerId ID = CustomerId.of(UUID.randomUUID());
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

    @Test
    void shouldSave_whenDataIsValid() {
        var customer = Customer.register(EMAIL, PHONE_NUMBER, USER_NAME, FULL_NAME, ADDRESS);
        var mockEntity = mock(CustomerJpaEntity.class);
        when(persistenceMapper.toJpaEntity(customer)).thenReturn(mockEntity);

        repository.save(customer);

        verify(persistenceMapper).toJpaEntity(customer);
        verify(commandRepository).save(mockEntity);
    }

    @Test
    void shouldReturnDomainObject_whenCustomerIsPresent() {
        var mockEntity = mock(CustomerJpaEntity.class);
        var mockDomainObject = mock(Customer.class);
        when(commandRepository.findById(ID.getValue())).thenReturn(Optional.of(mockEntity));
        when(persistenceMapper.toDomainObject(mockEntity)).thenReturn(mockDomainObject);

        var foundOptionalCustomer = repository.findById(ID);

        assertThat(foundOptionalCustomer).isPresent();
        assertThat(foundOptionalCustomer.get()).isSameAs(mockDomainObject);
        verify(commandRepository).findById(ID.getValue());
        verify(persistenceMapper).toDomainObject(mockEntity);
    }

    @Test
    void shouldReturnEmptyOptional_whenCustomerWasNotFound() {
        when(commandRepository.findById(ID.getValue())).thenReturn(Optional.empty());

        var foundOptionalCustomer = repository.findById(ID);

        assertThat(foundOptionalCustomer).isEmpty();
        verify(commandRepository).findById(ID.getValue());
        verify(persistenceMapper, never()).toDomainObject(any());
    }

    @Test
    void shouldReturnTrue_whenExistsByEmail() {
        when(commandRepository.existsByEmail(EMAIL)).thenReturn(true);

        var exists = repository.existsByEmail(EMAIL);

        assertThat(exists).isTrue();
        verify(commandRepository).existsByEmail(EMAIL);
    }

    @Test
    void shouldReturnFalse_whenDoesNotExistByEmail() {
        when(commandRepository.existsByEmail(EMAIL)).thenReturn(false);

        var exists = repository.existsByEmail(EMAIL);

        assertThat(exists).isFalse();
        verify(commandRepository).existsByEmail(EMAIL);
    }

    @Test
    void shouldReturnTrue_whenExistsByPhoneNumber() {
        when(commandRepository.existsByPhone(PHONE_NUMBER)).thenReturn(true);

        var exists = repository.existsByPhone(PHONE_NUMBER);

        assertThat(exists).isTrue();
        verify(commandRepository).existsByPhone(PHONE_NUMBER);
    }

    @Test
    void shouldReturnFalse_whenDoesNotExistByPhone() {
        when(commandRepository.existsByPhone(PHONE_NUMBER)).thenReturn(false);

        var exists = repository.existsByPhone(PHONE_NUMBER);

        assertThat(exists).isFalse();
        verify(commandRepository).existsByPhone(PHONE_NUMBER);
    }

    @Test
    void shouldReturnTrue_whenExistsByUserName() {
        when(commandRepository.existsByUserName(USER_NAME)).thenReturn(true);

        var exists = repository.existsByUserName(USER_NAME);

        assertThat(exists).isTrue();
        verify(commandRepository).existsByUserName(USER_NAME);
    }

    @Test
    void shouldReturnFalse_whenDoesNotExistByUserName() {
        when(commandRepository.existsByUserName(USER_NAME)).thenReturn(false);

        var exists = repository.existsByUserName(USER_NAME);

        assertThat(exists).isFalse();
        verify(commandRepository).existsByUserName(USER_NAME);
    }
}
