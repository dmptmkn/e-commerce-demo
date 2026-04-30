package com.example.infrastructure.mapper;

import com.example.domain.Address;
import com.example.domain.Customer;
import com.example.infrastructure.persistence.AddressJpaEmbeddable;
import com.example.infrastructure.persistence.CustomerJpaEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        builder = @Builder(disableBuilder = true)
) public interface CustomerPersistenceMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "address", expression = "java(toEmbeddable(customer.getAddress()))")
    CustomerJpaEntity toJpaEntity(Customer customer);

    default AddressJpaEmbeddable toEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressJpaEmbeddable.builder()
                .country(address.getCountry())
                .zipcode(address.getZipcode())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .apartment(address.getApartment())
                .build();
    }
}