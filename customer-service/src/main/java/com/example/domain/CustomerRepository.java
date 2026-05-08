package com.example.domain;

import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(CustomerId id);
    boolean existsByEmail(Email email);
    boolean existsByPhone(PhoneNumber phone);
    boolean existsByUserName(UserName name);
}
