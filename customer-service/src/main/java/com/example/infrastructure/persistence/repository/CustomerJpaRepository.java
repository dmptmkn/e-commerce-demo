package com.example.infrastructure.persistence.repository;

import com.example.infrastructure.persistence.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
}