package com.example.infrastructure.persistence.mapper;

import com.example.domain.Address;
import com.example.domain.Customer;
import com.example.domain.CustomerId;
import com.example.domain.FullName;
import com.example.infrastructure.persistence.AddressJpaEmbeddable;
import com.example.infrastructure.persistence.CustomerJpaEntity;
import com.example.infrastructure.persistence.FullNameJpaEmbeddable;
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
public interface CustomerPersistenceMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "fullName", expression = "java(toEmbeddableFullName(customer.getFullName()))")
    @Mapping(target = "address", expression = "java(toEmbeddableAddress(customer.getAddress()))")
    @Mapping(target = "deleted", source = "deleted")
    CustomerJpaEntity toJpaEntity(Customer customer);

    default Customer toDomainObject(CustomerJpaEntity jpaEntity) {
        return Customer.reconstruct(
                CustomerId.of(jpaEntity.getId()),
                jpaEntity.getEmail(),
                jpaEntity.getPhone(),
                jpaEntity.getUserName(),
                toDomainFullName(jpaEntity.getFullName()),
                jpaEntity.getStatus(),
                toDomainAddress(jpaEntity.getAddress()),
                jpaEntity.getLoyaltyPoints(),
                jpaEntity.isDeleted()
        );
    }

    default Address toDomainAddress(AddressJpaEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return Address.builder()
                .country(embeddable.getCountry())
                .zipcode(embeddable.getZipcode())
                .city(embeddable.getCity())
                .street(embeddable.getStreet())
                .building(embeddable.getBuilding())
                .apartment(embeddable.getApartment())
                .build();
    }

    default AddressJpaEmbeddable toEmbeddableAddress(Address address) {
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

    default FullName toDomainFullName(FullNameJpaEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return FullName.of(embeddable.getFirstName(), embeddable.getLastName());
    }

    default FullNameJpaEmbeddable toEmbeddableFullName(FullName name) {
        if (name == null) {
            return null;
        }
        return new FullNameJpaEmbeddable(name.getFirstName(), name.getLastName());
    }
}
