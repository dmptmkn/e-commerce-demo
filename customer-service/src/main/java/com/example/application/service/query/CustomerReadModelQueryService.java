package com.example.application.service.query;

import com.example.core.dto.CustomerProjectionDto;
import com.example.core.port.in.CustomerReadModelQueryPort;
import com.example.core.port.out.CustomerReadModelQueryOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerReadModelQueryService implements CustomerReadModelQueryPort {

    private final CustomerReadModelQueryOutPort outPort;

    @Override
    @Cacheable(value = "customerReadModel", key = "#id")
    public Optional<CustomerProjectionDto> findById(UUID id) {
        log.info("Looking for a customer");
        return outPort.findById(id);
    }

    @Override
    public List<CustomerProjectionDto> findAll() {
        return outPort.findAll();
    }

    @Override
    public Optional<CustomerProjectionDto> findByEmail(String email) {
        return outPort.findByEmail(email);
    }

    @Override
    public Optional<CustomerProjectionDto> findByUserName(String userName) {
        return outPort.findByUserName(userName);
    }
}
