package com.example.adapter.out.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    @Query("SELECT o FROM OutboxMessage o WHERE o.sent = false ORDER BY o.id ASC")
    List<OutboxMessage> findTop100BySentFalseOrderByIdAsc();
}
