package com.example.infrastructure.web;

import com.example.application.CustomerService;
import com.example.infrastructure.mapper.CustomerWebMapper;
import com.example.infrastructure.web.dto.RegisterCustomerDto;
import com.example.infrastructure.web.rest.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerWebMapper mapper;

    @PostMapping
    public ResponseEntity<SuccessResponse> register(@RequestBody @Valid RegisterCustomerDto request) {
        log.info("Customer registration request received: {}", request.email());
        var registerCommand = mapper.toCommand(request);
        service.register(registerCommand);
        log.info("Customer registered successfully: {}", request.email());
        return ResponseEntity.ok(SuccessResponse.of("OK", "201"));
    }
}