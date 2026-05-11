package com.example.application.command;

import com.example.domain.Address;
import com.example.domain.Email;
import com.example.domain.FullName;
import com.example.domain.PhoneNumber;
import com.example.domain.UserName;

public record RegisterCustomerCommand(Email email,
                                      PhoneNumber phone,
                                      UserName userName,
                                      FullName fullName,
                                      Address address) {
}
