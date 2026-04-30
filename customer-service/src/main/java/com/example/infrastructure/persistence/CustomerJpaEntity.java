package com.example.infrastructure.persistence;

import com.example.domain.CustomerStatus;
import com.example.domain.Email;
import com.example.domain.LoyaltyPoints;
import com.example.domain.PhoneNumber;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
public class CustomerJpaEntity extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private Email email;

    @Column(name = "phone", nullable = false, unique = true)
    private PhoneNumber phone;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CustomerStatus status;

    @Embedded
    private AddressJpaEmbeddable address;

    @Column(name = "loyalty_points", nullable = false)
    private LoyaltyPoints loyaltyPoints;
}