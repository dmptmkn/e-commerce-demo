package com.example.adapter.in.graphql;

import com.example.core.domain.exception.CustomerNotFoundException;
import com.example.core.dto.CustomerProjectionDto;
import com.example.core.port.in.CustomerReadModelQueryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GraphQLResolver {

    private final CustomerReadModelQueryPort queryPort;

    @QueryMapping
    public CustomerProjectionDto customer(@Argument UUID id) {
        log.debug("Fetching customer with id {}", id);
        return queryPort.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @QueryMapping
    public List<CustomerProjectionDto> customers() {
        log.debug("Fetching all customers");
        return queryPort.findAll();
    }

    @QueryMapping
    public CustomerProjectionDto customerByEmail(@Argument String email) {
        log.debug("Fetching customer with email {}", email);
        return queryPort.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
    }

    @QueryMapping
    public CustomerProjectionDto customerByUserName(@Argument String userName) {
        log.debug("Fetching customer with username {}", userName);
        return queryPort.findByUserName(userName).orElseThrow(CustomerNotFoundException::new);
    }
}
