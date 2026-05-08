package com.example.infrastructure.persistence.repository;

import com.example.domain.UserName;
import com.example.domain.Email;
import com.example.domain.PhoneNumber;
import com.example.infrastructure.persistence.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerCommandRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    boolean existsByEmail(Email email);
    boolean existsByPhone(PhoneNumber phone);
    boolean existsByUserName(UserName name);
}
