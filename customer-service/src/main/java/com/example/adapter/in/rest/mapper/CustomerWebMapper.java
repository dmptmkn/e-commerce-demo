package com.example.adapter.in.rest.mapper;

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
import com.example.adapter.in.rest.dto.UnblockCustomerDto;
import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.BlockCustomerCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerFullNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerUserNameCommand;
import com.example.application.command.DeleteCustomerCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;
import com.example.application.command.UnblockCustomerCommand;
import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.FullName;
import com.example.core.domain.valueobject.LoyaltyPoints;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.Reason;
import com.example.core.domain.valueobject.UserName;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true)
)
public interface CustomerWebMapper {

    default RegisterCustomerCommand toCommand(RegisterCustomerDto request) {
        return new RegisterCustomerCommand(
                Email.of(request.email()),
                PhoneNumber.of(request.phone()),
                UserName.of(request.userName()),
                FullName.of(request.firstName(), request.lastName()),
                Address.builder()
                        .country(request.country())
                        .zipcode(request.zipcode())
                        .city(request.city())
                        .street(request.street())
                        .building(request.building())
                        .apartment(request.apartment())
                        .build()
        );
    }

    default ChangeCustomerEmailCommand toCommand(UUID id, ChangeCustomerEmailDto request) {
        return new ChangeCustomerEmailCommand(CustomerId.of(id), Email.of(request.newEmail()));
    }

    default ChangeCustomerPhoneCommand toCommand(UUID id, ChangeCustomerPhoneDto request) {
        return new ChangeCustomerPhoneCommand(CustomerId.of(id), PhoneNumber.of(request.newPhone()));
    }

    default ChangeCustomerUserNameCommand toCommand(UUID id, ChangeCustomerUserNameDto request) {
        return new ChangeCustomerUserNameCommand(CustomerId.of(id), UserName.of(request.newUserName()));
    }

    default ChangeCustomerFullNameCommand toCommand(UUID id, ChangeCustomerFullNameDto request) {
        return new ChangeCustomerFullNameCommand(
                CustomerId.of(id),
                FullName.of(request.firstName(), request.lastName())
        );
    }

    default BlockCustomerCommand toCommand(UUID id, BlockCustomerDto request) {
        return new BlockCustomerCommand(CustomerId.of(id), Reason.of(request.reason()));
    }

    default UnblockCustomerCommand toCommand(UUID id, UnblockCustomerDto request) {
        return new UnblockCustomerCommand(CustomerId.of(id), Reason.of(request.reason()));
    }

    default DeleteCustomerCommand toCommand(UUID id, DeleteCustomerDto request) {
        return new DeleteCustomerCommand(CustomerId.of(id), Reason.of(request.reason()));
    }

    default ChangeCustomerAddressCommand toCommand(UUID id, ChangeCustomerAddressDto request) {
        return new ChangeCustomerAddressCommand(
                CustomerId.of(id),
                Address.builder()
                        .country(request.country())
                        .zipcode(request.zipcode())
                        .city(request.city())
                        .street(request.street())
                        .building(request.building())
                        .apartment(request.apartment())
                        .build()
        );
    }

    default AddLoyaltyPointsCommand toCommand(UUID id, AddLoyaltyPointsDto request) {
        return new AddLoyaltyPointsCommand(CustomerId.of(id), LoyaltyPoints.of(request.points()));
    }

    default SubtractLoyaltyPointsCommand toCommand(UUID id, SubtractLoyaltyPointsDto request) {
        return new SubtractLoyaltyPointsCommand(CustomerId.of(id), LoyaltyPoints.of(request.points()));
    }
}
