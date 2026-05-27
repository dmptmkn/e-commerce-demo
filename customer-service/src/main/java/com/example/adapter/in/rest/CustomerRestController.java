package com.example.adapter.in.rest;

import com.example.adapter.in.rest.dto.AddLoyaltyPointsDto;
import com.example.adapter.in.rest.dto.BlockCustomerDto;
import com.example.adapter.in.rest.dto.ChangeCustomerAddressDto;
import com.example.adapter.in.rest.dto.ChangeCustomerEmailDto;
import com.example.adapter.in.rest.dto.ChangeCustomerFullNameDto;
import com.example.adapter.in.rest.dto.ChangeCustomerPhoneDto;
import com.example.adapter.in.rest.dto.ChangeCustomerUserNameDto;
import com.example.adapter.in.rest.dto.DeleteCustomerDto;
import com.example.adapter.in.rest.dto.RegisterCustomerDto;
import com.example.adapter.in.rest.dto.SubtractLoyaltyPointsDto;
import com.example.adapter.in.rest.dto.SuccessResponse;
import com.example.adapter.in.rest.dto.UnblockCustomerDto;
import com.example.adapter.in.rest.mapper.CustomerWebMapper;
import com.example.core.port.in.CustomerCommandPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

    private final CustomerCommandPort service;
    private final CustomerWebMapper mapper;

    @PostMapping
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterCustomerDto request) {
        var email = request.email();
        log.debug("Customer registration request received: {}", email);
        var registerCommand = mapper.toCommand(request);
        service.register(registerCommand);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(HttpStatus.CREATED, "Customer with email %s created".formatted(email)));
    }

    @PatchMapping(value = "/{id}/email")
    public ResponseEntity<SuccessResponse> changeEmail(@PathVariable UUID id,
                                                       @RequestBody ChangeCustomerEmailDto request) {
        log.debug("Customer email change request received: {}", id);
        var changeEmailCommand = mapper.toCommand(id, request);
        service.changeEmail(changeEmailCommand);
        return successResponse("Customer email updated");
    }

    @PatchMapping(value = "/{id}/phone")
    public ResponseEntity<SuccessResponse> changePhone(@PathVariable UUID id,
                                                       @RequestBody ChangeCustomerPhoneDto request) {
        log.debug("Customer phone change request received: {}", id);
        var changePhoneCommand = mapper.toCommand(id, request);
        service.changePhone(changePhoneCommand);
        return successResponse("Customer phone updated");
    }

    @PatchMapping(value = "/{id}/username")
    public ResponseEntity<SuccessResponse> changeUserName(@PathVariable UUID id,
                                                          @RequestBody ChangeCustomerUserNameDto request) {
        log.debug("Customer username change request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.changeUserName(command);
        return successResponse("Customer username changed");
    }

    @PatchMapping(value = "/{id}/fullname")
    public ResponseEntity<SuccessResponse> changeFullName(@PathVariable UUID id,
                                                          @RequestBody ChangeCustomerFullNameDto request) {
        log.debug("Customer full name request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.changeFullName(command);
        return successResponse("Customer full name changed");
    }

    @PatchMapping(value = "/{id}/address")
    public ResponseEntity<SuccessResponse> changeAddress(@PathVariable UUID id,
                                                         @RequestBody ChangeCustomerAddressDto request) {
        log.debug("Customer address change request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.changeAddress(command);
        return successResponse("Customer address changed");
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<SuccessResponse> block(@PathVariable UUID id,
                                                 @RequestBody BlockCustomerDto request) {
        log.debug("Customer block request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.block(command);
        return successResponse("Customer blocked");
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<SuccessResponse> unblock(@PathVariable UUID id,
                                                   @RequestBody UnblockCustomerDto request) {
        log.debug("Customer unblock request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.unblock(command);
        return successResponse("Customer unblocked");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable UUID id,
                                                  @RequestBody DeleteCustomerDto request) {
        log.debug("Customer deletion request received: {}", id);
        var command = mapper.toCommand(id, request);
        service.delete(command);
        return successResponse("Customer deleted");
    }

    @PostMapping("/{id}/loyalty/add")
    public ResponseEntity<SuccessResponse> addLoyaltyPoints(@PathVariable UUID id,
                                                            @RequestBody AddLoyaltyPointsDto request) {
        log.debug("Add loyalty points request for customer: {}, points: {}", id, request.points());
        var command = mapper.toCommand(id, request);
        service.addLoyaltyPoints(command);
        return successResponse("Loyalty points added");
    }

    @PostMapping("/{id}/loyalty/subtract")
    public ResponseEntity<SuccessResponse> subtractLoyaltyPoints(@PathVariable UUID id,
                                                                 @RequestBody SubtractLoyaltyPointsDto request) {
        log.debug("Subtract loyalty points request for customer: {}, points: {}", id, request.points());
        var command = mapper.toCommand(id, request);
        service.subtractLoyaltyPoints(command);
        return successResponse("Loyalty points subtracted");
    }

    private ResponseEntity<SuccessResponse> successResponse(String message) {
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK, message));
    }
}
