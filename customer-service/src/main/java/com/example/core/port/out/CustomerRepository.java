package com.example.core.port.out;

import com.example.core.domain.aggregate.Customer;
import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;

import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    boolean existsByEmail(Email email);
    boolean existsByPhone(PhoneNumber phone);
    boolean existsByUserName(UserName name);
}
