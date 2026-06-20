package com.example.adapter.out.persistence.write;

import com.example.adapter.out.persistence.write.embeddable.AddressJpaEmbeddable;
import com.example.adapter.out.persistence.write.embeddable.FullNameJpaEmbeddable;
import com.example.core.domain.enumeration.CustomerStatus;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.LoyaltyPoints;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;
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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
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

    @Column(name = "username", nullable = false, unique = true)
    private UserName userName;

    @Embedded
    private FullNameJpaEmbeddable fullName;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CustomerStatus status;

    @Embedded
    private AddressJpaEmbeddable address;

    @Column(name = "loyalty_points", nullable = false)
    private LoyaltyPoints loyaltyPoints;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}