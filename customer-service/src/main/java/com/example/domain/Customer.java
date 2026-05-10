package com.example.domain;

import com.example.domain.event.CustomerAddressChangedEvent;
import com.example.domain.event.CustomerBlockedEvent;
import com.example.domain.event.CustomerDeletedEvent;
import com.example.domain.event.CustomerEmailChangedEvent;
import com.example.domain.event.CustomerFullNameChangedEvent;
import com.example.domain.event.CustomerPhoneChangedEvent;
import com.example.domain.event.CustomerRegisteredEvent;
import com.example.domain.event.CustomerUnblockedEvent;
import com.example.domain.event.CustomerUserNameChangedEvent;
import com.example.domain.event.DomainEvent;
import com.example.domain.event.LoyaltyPointsAddedEvent;
import com.example.domain.event.LoyaltyPointsSubtractedEvent;
import com.example.domain.exception.CustomerAlreadyBlockedException;
import com.example.domain.exception.CustomerAlreadyDeletedException;
import com.example.domain.exception.CustomerNotBlockedException;
import com.example.domain.exception.SameAddressException;
import com.example.domain.exception.SameEmailException;
import com.example.domain.exception.SameFullNameException;
import com.example.domain.exception.SamePhoneNumberException;
import com.example.domain.exception.SameUserNameException;
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
    private boolean deleted;
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
                LoyaltyPoints.ZERO,
                false
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
                                       LoyaltyPoints loyaltyPoints,
                                       boolean deleted) {
        return new Customer(id, email, phone, userName, fullName, status, address, loyaltyPoints, deleted);
    }

    public List<DomainEvent> pullEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    public void changeEmail(Email newEmail) {
        ensureNotDeleted();
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
        ensureNotDeleted();
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
        ensureNotDeleted();
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
        ensureNotDeleted();
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
        ensureNotDeleted();
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

    public void block(Reason reason) {
        ensureNotDeleted();
        if (this.status == CustomerStatus.BLOCKED) {
            throw new CustomerAlreadyBlockedException(this.id);
        }
        this.status = CustomerStatus.BLOCKED;

        registerEvent(CustomerBlockedEvent.builder()
                .aggregateId(this.getId().getValue())
                .reason(reason.getValue())
                .build()
        );
    }

    public void unblock(Reason reason) {
        ensureNotDeleted();
        if (this.status != CustomerStatus.BLOCKED) {
            throw new CustomerNotBlockedException(this.id);
        }
        this.status = CustomerStatus.ACTIVE;

        registerEvent(CustomerUnblockedEvent.builder()
                .aggregateId(this.getId().getValue())
                .reason(reason.getValue())
                .build()
        );
    }

    public void softDelete(Reason reason) {
        if (this.deleted) {
            throw new CustomerAlreadyDeletedException(this.id);
        }
        this.deleted = true;

        registerEvent(CustomerDeletedEvent.builder()
                .aggregateId(this.getId().getValue())
                .reason(reason.getValue())
                .build()
        );
    }

    public void addLoyaltyPoints(int points) {
        ensureNotDeleted();
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
        ensureNotDeleted();
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

    private void ensureNotDeleted() {
        if (deleted) {
            throw new CustomerAlreadyDeletedException(this.id);
        }
    }
}
