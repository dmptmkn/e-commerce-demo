package com.example.infrastructure.persistence.repository;

import com.example.infrastructure.persistence.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findTop100BySentFalseOrderByIdAsc();
}
