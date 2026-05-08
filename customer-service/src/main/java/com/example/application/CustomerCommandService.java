package com.example.application;

import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerFullNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerStatusCommand;
import com.example.application.command.ChangeCustomerUserNameCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;

public interface CustomerCommandService {
    void register(RegisterCustomerCommand command);
    void changeEmail(ChangeCustomerEmailCommand command);
    void changePhone(ChangeCustomerPhoneCommand command);
    void changeUserName(ChangeCustomerUserNameCommand command);
    void changeFullName(ChangeCustomerFullNameCommand command);
    void changeAddress(ChangeCustomerAddressCommand command);
    void changeStatus(ChangeCustomerStatusCommand command);
    void addLoyaltyPoints(AddLoyaltyPointsCommand command);
    void subtractLoyaltyPoints(SubtractLoyaltyPointsCommand command);
}
