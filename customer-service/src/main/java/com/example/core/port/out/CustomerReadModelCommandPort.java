package com.example.core.port.out;

import com.example.core.dto.AddLoyaltyPointsReadModelDto;
import com.example.core.dto.BlockCustomerReadModelDto;
import com.example.core.dto.CreateCustomerReadModelDto;
import com.example.core.dto.MarkCustomerDeletedReadModelDto;
import com.example.core.dto.SubtractLoyaltyPointsReadModelDto;
import com.example.core.dto.UnblockCustomerReadModelDto;
import com.example.core.dto.UpdateCustomerAddressReadModelDto;
import com.example.core.dto.UpdateCustomerEmailReadModelDto;
import com.example.core.dto.UpdateCustomerFullNameReadModelDto;
import com.example.core.dto.UpdateCustomerPhoneReadModelDto;
import com.example.core.dto.UpdateCustomerUserNameReadModelDto;

public interface CustomerReadModelCommandPort {
    void createCustomer(CreateCustomerReadModelDto dto);
    void updateEmail(UpdateCustomerEmailReadModelDto dto);
    void updatePhone(UpdateCustomerPhoneReadModelDto dto);
    void updateUserName(UpdateCustomerUserNameReadModelDto dto);
    void updateFullName(UpdateCustomerFullNameReadModelDto dto);
    void updateAddress(UpdateCustomerAddressReadModelDto dto);
    void blockCustomer(BlockCustomerReadModelDto dto);
    void unblockCustomer(UnblockCustomerReadModelDto dto);
    void markDeleted(MarkCustomerDeletedReadModelDto dto);
    void addLoyaltyPoints(AddLoyaltyPointsReadModelDto dto);
    void subtractLoyaltyPoints(SubtractLoyaltyPointsReadModelDto dto);
}
