package com.example.domain;

import com.example.domain.event.CustomerAddressChangedEvent;
import com.example.domain.event.CustomerEmailChangedEvent;
import com.example.domain.event.CustomerFullNameChangedEvent;
import com.example.domain.event.CustomerUserNameChangedEvent;
import com.example.domain.event.CustomerPhoneChangedEvent;
import com.example.domain.event.CustomerRegisteredEvent;
import com.example.domain.event.CustomerStatusChangedEvent;
import com.example.domain.event.DomainEvent;
import com.example.domain.event.LoyaltyPointsAddedEvent;
import com.example.domain.event.LoyaltyPointsSubtractedEvent;
import com.example.domain.exception.SameAddressException;
import com.example.domain.exception.SameEmailException;
import com.example.domain.exception.SameFullNameException;
import com.example.domain.exception.SameUserNameException;
import com.example.domain.exception.SamePhoneNumberException;
import com.example.domain.exception.SameStatusException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    private final CustomerId id;
    private Email email;
    private PhoneNumber phone;
    private UserName userName;
    private FullName fullName;
    private CustomerStatus status;
    private Address address;
    private LoyaltyPoints loyaltyPoints;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public static Customer register(Email email,
                                    PhoneNumber phone,
                                    UserName userName,
                                    FullName fullName,
                                    Address address
    ) {
        var newCustomer = new Customer(
                CustomerId.of(UUID.randomUUID()),
                email,
                phone,
                userName,
                fullName,
                CustomerStatus.ACTIVE,
                address,
                LoyaltyPoints.ZERO
        );

        newCustomer.registerEvent(
                CustomerRegisteredEvent.builder()
                        .aggregateId(newCustomer.getId().getValue())
                        .email(email.getValue())
                        .phone(phone.getValue())
                        .userName(userName.getValue())
                        .fullName(fullName.getFullName())
                        .build()
        );
        return newCustomer;
    }

    public static Customer reconstruct(CustomerId id,
                                       Email email,
                                       PhoneNumber phone,
                                       UserName userName,
                                       FullName fullName,
                                       CustomerStatus status,
                                       Address address,
                                       LoyaltyPoints loyaltyPoints) {
        return new Customer(id, email, phone, userName, fullName, status, address, loyaltyPoints);
    }

    public List<DomainEvent> pullEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    public void changeEmail(Email newEmail) {
        if (this.email.equals(newEmail)) {
            throw new SameEmailException(newEmail);
        }

        var oldEmail = this.email;
        this.email = newEmail;
        registerEvent(
                CustomerEmailChangedEvent.builder()
                        .aggregateId(this.id.getValue())
                        .oldEmail(oldEmail.getValue())
                        .newEmail(newEmail.getValue())
                        .build()
        );
    }

    public void changePhoneNumber(PhoneNumber newNumber) {
        if (this.phone.equals(newNumber)) {
            throw new SamePhoneNumberException(newNumber);
        }

        var oldPhone = this.phone;
        this.phone = newNumber;
        registerEvent(
                CustomerPhoneChangedEvent.builder()
                        .aggregateId(this.getId().getValue())
                        .oldNumber(oldPhone.getValue())
                        .newNumber(newNumber.getValue())
                        .build()
        );
    }

    public void changeUserName(UserName newName) {
        if (this.userName.equals(newName)) {
            throw new SameUserNameException(newName);
        }

        var oldName = this.userName;
        this.userName = newName;
        registerEvent(
                CustomerUserNameChangedEvent.builder()
                        .aggregateId(this.id.getValue())
                        .oldName(oldName.getValue())
                        .newName(newName.getValue())
                        .build()
        );
    }

    public void changeFullName(FullName newName) {
        if (this.fullName.equals(newName)) {
            throw new SameFullNameException(newName);
        }
        var oldName = this.fullName;
        this.fullName = newName;

        registerEvent(
                CustomerFullNameChangedEvent.builder()
                        .aggregateId(this.id.getValue())
                        .oldName(oldName.getFullName())
                        .newName(newName.getFullName())
                        .build()
        );
    }

    public void changeAddress(Address newAddress) {
        if (this.address.equals(newAddress)) {
            throw new SameAddressException(newAddress);
        }

        this.address = newAddress;
        registerEvent(
                CustomerAddressChangedEvent.builder()
                        .aggregateId(this.getId().getValue())
                        .country(newAddress.getCountry())
                        .zipcode(newAddress.getZipcode())
                        .city(newAddress.getCity())
                        .street(newAddress.getStreet())
                        .building(newAddress.getBuilding())
                        .apartment(newAddress.getApartment())
                        .build()
        );
    }

    public void changeStatus(CustomerStatus newStatus) {
        if (this.status == newStatus) {
            throw new SameStatusException(newStatus);
        }

        this.status = newStatus;
        registerEvent(
                CustomerStatusChangedEvent.builder()
                        .aggregateId(this.getId().getValue())
                        .newStatus(newStatus.name())
                        .build()
        );
    }

    public void addLoyaltyPoints(int points) {
        var newBalance = this.loyaltyPoints.add(points);
        this.loyaltyPoints = newBalance;

        registerEvent(
                LoyaltyPointsAddedEvent.builder()
                        .aggregateId(this.getId().getValue())
                        .pointsAdded(points)
                        .newBalance(newBalance.getValue())
                        .build()
        );
    }

    public void subtractLoyaltyPoints(int points) {
        var newBalance = this.loyaltyPoints.subtract(points);
        this.loyaltyPoints = newBalance;

        registerEvent(
                LoyaltyPointsSubtractedEvent.builder()
                        .aggregateId(this.getId().getValue())
                        .pointsSubtracted(points)
                        .newBalance(newBalance.getValue())
                        .build()
        );
    }

    private void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }
}
