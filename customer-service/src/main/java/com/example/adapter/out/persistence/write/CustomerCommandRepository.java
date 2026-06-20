package com.example.adapter.out.persistence.write;

import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerCommandRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    boolean existsByEmail(Email email);
    boolean existsByPhone(PhoneNumber phone);
    boolean existsByUserName(UserName name);
}
