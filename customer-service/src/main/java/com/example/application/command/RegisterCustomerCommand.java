package com.example.application.command;

import com.example.core.domain.valueobject.Address;
import com.example.core.domain.valueobject.Email;
import com.example.core.domain.valueobject.FullName;
import com.example.core.domain.valueobject.PhoneNumber;
import com.example.core.domain.valueobject.UserName;

public record RegisterCustomerCommand(Email email,
                                      PhoneNumber phone,
                                      UserName userName,
                                      FullName fullName,
                                      Address address) {
}
