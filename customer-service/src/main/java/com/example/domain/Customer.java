package com.example.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

import static com.example.domain.util.Preconditions.requireNotBlank;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {
    private final UUID id;
    private Email email;
    private PhoneNumber phone;
    private String name;
    private CustomerStatus status;
    private Address address;
    private LoyaltyPoints loyaltyPoints;

    public static Customer register(
            Email email,
            PhoneNumber phone,
            String name,
            Address address) {
        requireNotBlank(name, "Name is required");
        var normalizedName = name.trim();

        return new Customer(
                UUID.randomUUID(),
                email,
                phone,
                normalizedName,
                CustomerStatus.ACTIVE,
                address,
                LoyaltyPoints.ZERO
        );
    }

    public void changeEmail(Email newEmail) {
        if (this.email.equals(newEmail)) {
            throw new IllegalArgumentException("New email must be different from the existing one");
        }
        this.email = newEmail;
    }

    public void changePhoneNumber(PhoneNumber newNumber) {
        if (this.phone.equals(newNumber)) {
            throw new IllegalArgumentException("New phone number must be different from the existing one");
        }
        this.phone = newNumber;
    }

    public void changeName(String newName) {
        var normalized = name == null ? null : name.trim();
        requireNotBlank(normalized, "Name is required");
        if (this.name.equals(normalized)) {
            throw new IllegalArgumentException("New name must be different from the existing one");
        }
        this.name = normalized;
    }

    public void changeAddress(Address newAddress) {
        if (this.address.equals(newAddress)) {
            throw new IllegalArgumentException("New address must be different from the existing one");
        }
        this.address = newAddress;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints = this.loyaltyPoints.add(points);
    }

    public void subtractLoyaltyPoints(int points) {
        this.loyaltyPoints = this.loyaltyPoints.subtract(points);
    }
}