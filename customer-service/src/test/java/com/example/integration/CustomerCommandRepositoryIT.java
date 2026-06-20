package com.example.integration;

import com.example.adapter.out.persistence.write.CustomerCommandRepository;
import com.example.adapter.out.persistence.write.CustomerJpaEntity;
import com.example.adapter.out.persistence.write.embeddable.AddressJpaEmbeddable;
import com.example.adapter.out.persistence.write.embeddable.FullNameJpaEmbeddable;
import com.example.core.domain.enumeration.CustomerStatus;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.LoyaltyPoints;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerCommandRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18.3")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgres::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", postgres::getUsername);
        propertyRegistry.add("spring.datasource.password", postgres::getPassword);
        propertyRegistry.add("spring.liquibase.enabled",
                () -> "true");
        propertyRegistry.add("spring.liquibase.change-log",
                () -> "classpath:db/changelog/db.changelog-master.yaml");
        propertyRegistry.add("spring.jpa.hibernate.ddl-auto",
                () -> "none");
    }

    private final static UUID ID = UUID.randomUUID();
    private final static Email EMAIL = Email.of("test@test.com");
    private final static PhoneNumber PHONE_NUMBER = PhoneNumber.of("+78002002316");
    private final static UserName USER_NAME = UserName.of("test");
    private final static FullNameJpaEmbeddable FULL_NAME = new FullNameJpaEmbeddable("Test", "Test");
    private final static AddressJpaEmbeddable ADDRESS = AddressJpaEmbeddable.builder()
            .country("Россия")
            .zipcode("103132")
            .city("Москва")
            .street("Красная площадь")
            .building("7")
            .apartment("1")
            .build();
    private final static LoyaltyPoints LOYALTY_POINTS = LoyaltyPoints.of(10);

    @Autowired
    private CustomerCommandRepository repository;

    @Test
    void shouldSaveAndThenFindById() {
        var entity = createTestEntity();

        repository.save(entity);
        var found = repository.findById(ID).orElseThrow();

        assertThat(found)
                .extracting(
                        CustomerJpaEntity::getId,
                        CustomerJpaEntity::getEmail,
                        CustomerJpaEntity::getPhone,
                        CustomerJpaEntity::getUserName,
                        CustomerJpaEntity::getFullName,
                        CustomerJpaEntity::getStatus,
                        CustomerJpaEntity::getAddress,
                        CustomerJpaEntity::getLoyaltyPoints,
                        CustomerJpaEntity::isDeleted
                )
                .containsExactly(
                        ID,
                        EMAIL,
                        PHONE_NUMBER,
                        USER_NAME,
                        FULL_NAME,
                        CustomerStatus.ACTIVE,
                        ADDRESS,
                        LOYALTY_POINTS,
                        false
                );
    }

    @Test
    void shouldReturnTrue_whenEmailExists() {
        var entity = createTestEntity();

        repository.save(entity);
        var exists = repository.existsByEmail(EMAIL);

        assertThat(exists).isTrue();
    }

    private CustomerJpaEntity createTestEntity() {
        var entity = CustomerJpaEntity.builder()
                .email(EMAIL)
                .phone(PHONE_NUMBER)
                .userName(USER_NAME)
                .fullName(FULL_NAME)
                .status(CustomerStatus.ACTIVE)
                .address(ADDRESS)
                .loyaltyPoints(LOYALTY_POINTS)
                .deleted(false)
                .build();
        entity.setId(ID);

        return entity;
    }
}