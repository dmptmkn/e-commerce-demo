package com.example.application;

import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerStatusCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;

public interface CustomerCommandService {
    void register(RegisterCustomerCommand command);
    void changeEmail(ChangeCustomerEmailCommand command);
    void changePhone(ChangeCustomerPhoneCommand command);
    void changeName(ChangeCustomerNameCommand command);
    void changeAddress(ChangeCustomerAddressCommand command);
    void changeStatus(ChangeCustomerStatusCommand command);
    void addLoyaltyPoints(AddLoyaltyPointsCommand command);
    void subtractLoyaltyPoints(SubtractLoyaltyPointsCommand command);
}
