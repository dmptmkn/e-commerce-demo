package com.example.application;

import com.example.application.command.RegisterCustomerCommand;

public interface CustomerService {
    void register(RegisterCustomerCommand command);
}