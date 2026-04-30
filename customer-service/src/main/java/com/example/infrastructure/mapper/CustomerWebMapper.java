package com.example.infrastructure.mapper;

import com.example.application.command.RegisterCustomerCommand;
import com.example.infrastructure.web.dto.RegisterCustomerDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true)
)
public interface CustomerWebMapper {
    RegisterCustomerCommand toCommand(RegisterCustomerDto request);
}