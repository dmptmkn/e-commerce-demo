package com.example.core.port.in;

import com.example.application.command.AddLoyaltyPointsCommand;
import com.example.application.command.BlockCustomerCommand;
import com.example.application.command.ChangeCustomerAddressCommand;
import com.example.application.command.ChangeCustomerEmailCommand;
import com.example.application.command.ChangeCustomerFullNameCommand;
import com.example.application.command.ChangeCustomerPhoneCommand;
import com.example.application.command.ChangeCustomerUserNameCommand;
import com.example.application.command.DeleteCustomerCommand;
import com.example.application.command.RegisterCustomerCommand;
import com.example.application.command.SubtractLoyaltyPointsCommand;
import com.example.application.command.UnblockCustomerCommand;

public interface CustomerCommandPort {
    void register(RegisterCustomerCommand command);
    void block(BlockCustomerCommand command);
    void unblock(UnblockCustomerCommand command);
    void delete(DeleteCustomerCommand command);
    void changeEmail(ChangeCustomerEmailCommand command);
    void changePhone(ChangeCustomerPhoneCommand command);
    void changeUserName(ChangeCustomerUserNameCommand command);
    void changeFullName(ChangeCustomerFullNameCommand command);
    void changeAddress(ChangeCustomerAddressCommand command);
    void addLoyaltyPoints(AddLoyaltyPointsCommand command);
    void subtractLoyaltyPoints(SubtractLoyaltyPointsCommand command);
}
