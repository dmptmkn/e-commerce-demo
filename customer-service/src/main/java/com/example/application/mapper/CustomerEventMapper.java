package com.example.application.mapper;

import com.example.core.domain.event.CustomerAddressChangedEvent;
import com.example.core.domain.event.CustomerBlockedEvent;
import com.example.core.domain.event.CustomerDeletedEvent;
import com.example.core.domain.event.CustomerEmailChangedEvent;
import com.example.core.domain.event.CustomerFullNameChangedEvent;
import com.example.core.domain.event.CustomerPhoneChangedEvent;
import com.example.core.domain.event.CustomerRegisteredEvent;
import com.example.core.domain.event.CustomerUnblockedEvent;
import com.example.core.domain.event.CustomerUserNameChangedEvent;
import com.example.core.domain.event.LoyaltyPointsAddedEvent;
import com.example.core.domain.event.LoyaltyPointsSubtractedEvent;
import com.example.core.dto.AddLoyaltyPointsReadModelDto;
import com.example.core.dto.BlockCustomerReadModelDto;
import com.example.core.dto.CreateCustomerReadModelDto;
import com.example.core.dto.MarkCustomerDeletedReadModelDto;
import com.example.core.dto.SubtractLoyaltyPointsReadModelDto;
import com.example.core.dto.UnblockCustomerReadModelDto;
import com.example.core.dto.UpdateCustomerAddressReadModelDto;
import com.example.core.dto.UpdateCustomerEmailReadModelDto;
import com.example.core.dto.UpdateCustomerFullNameReadModelDto;
import com.example.core.dto.UpdateCustomerPhoneReadModelDto;
import com.example.core.dto.UpdateCustomerUserNameReadModelDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true)
)
public interface CustomerEventMapper {
    @Mapping(source = "aggregateId", target = "customerId")
    CreateCustomerReadModelDto toDto(CustomerRegisteredEvent event);

    default UpdateCustomerEmailReadModelDto toDto(CustomerEmailChangedEvent event) {
        return new UpdateCustomerEmailReadModelDto(event.getAggregateId(), event.getNewEmail());
    }

    default UpdateCustomerPhoneReadModelDto toDto(CustomerPhoneChangedEvent event) {
        return new UpdateCustomerPhoneReadModelDto(event.getAggregateId(), event.getNewNumber());
    }

    default UpdateCustomerUserNameReadModelDto toDto(CustomerUserNameChangedEvent event) {
        return new UpdateCustomerUserNameReadModelDto(event.getAggregateId(), event.getNewName());
    }

    default UpdateCustomerFullNameReadModelDto toDto(CustomerFullNameChangedEvent event) {
        return new UpdateCustomerFullNameReadModelDto(
                event.getAggregateId(),
                event.getNewFirstName(),
                event.getNewLastName()
        );
    }

    default UpdateCustomerAddressReadModelDto toDto(CustomerAddressChangedEvent event) {
        return new UpdateCustomerAddressReadModelDto(
                event.getAggregateId(),
                event.getCountry(),
                event.getZipcode(),
                event.getCity(),
                event.getStreet(),
                event.getBuilding(),
                event.getApartment()
        );
    }

    default BlockCustomerReadModelDto toDto(CustomerBlockedEvent event) {
        return new BlockCustomerReadModelDto(event.getAggregateId(), event.getReason());
    }

    default UnblockCustomerReadModelDto toDto(CustomerUnblockedEvent event) {
        return new UnblockCustomerReadModelDto(event.getAggregateId(), event.getReason());
    }

    default MarkCustomerDeletedReadModelDto toDto(CustomerDeletedEvent event) {
        return new MarkCustomerDeletedReadModelDto(event.getAggregateId(), event.getReason());
    }

    default AddLoyaltyPointsReadModelDto toDto(LoyaltyPointsAddedEvent event) {
        return new AddLoyaltyPointsReadModelDto(event.getAggregateId(), event.getPointsAdded());
    }

    default SubtractLoyaltyPointsReadModelDto toDto(LoyaltyPointsSubtractedEvent event) {
        return new SubtractLoyaltyPointsReadModelDto(event.getAggregateId(),event.getPointsSubtracted());
    }
}
