package com.example.core.port.in;

import com.example.core.dto.CustomerProjectionDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerReadModelQueryPort {
    Optional<CustomerProjectionDto> findById(UUID id);
    List<CustomerProjectionDto> findAll();
    Optional<CustomerProjectionDto> findByEmail(String email);
    Optional<CustomerProjectionDto> findByUserName(String userName);
}
