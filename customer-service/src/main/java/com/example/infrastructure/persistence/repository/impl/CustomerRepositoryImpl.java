package com.example.infrastructure.persistence.repository.impl;

import com.example.domain.Customer;
import com.example.domain.CustomerId;
import com.example.domain.UserName;
import com.example.domain.CustomerRepository;
import com.example.domain.Email;
import com.example.domain.PhoneNumber;
import com.example.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import com.example.infrastructure.persistence.repository.CustomerCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerCommandRepository commandRepository;
    private final CustomerPersistenceMapper persistenceMapper;

    @Override
    public void save(Customer customer) {
        var optionalEntity = commandRepository.findById(customer.getId().getValue());

        if (optionalEntity.isPresent()) {
            var entity = optionalEntity.get();
            entity.setEmail(customer.getEmail());
            entity.setPhone(customer.getPhone());
            entity.setUserName(customer.getUserName());
            entity.setFullName(persistenceMapper.toEmbeddableFullName(customer.getFullName()));
            entity.setStatus(customer.getStatus());
            entity.setAddress(persistenceMapper.toEmbeddableAddress(customer.getAddress()));
            entity.setLoyaltyPoints(customer.getLoyaltyPoints());
            entity.setDeleted(customer.isDeleted());
            commandRepository.save(entity);
        } else {
            var newEntity = persistenceMapper.toJpaEntity(customer);
            commandRepository.save(newEntity);
        }
    }

    @Override
    public Optional<Customer> findById(CustomerId id) {
        var entity = commandRepository.findById(id.getValue());
        return entity.map(persistenceMapper::toDomainObject);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return commandRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(PhoneNumber phone) {
        return commandRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByUserName(UserName name) {
        return commandRepository.existsByUserName(name);
    }
}
