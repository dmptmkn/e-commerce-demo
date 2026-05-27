package com.example.application.command;

import com.example.core.domain.valueobject.CustomerId;
import com.example.core.domain.valueobject.LoyaltyPoints;

public record SubtractLoyaltyPointsCommand(CustomerId id, LoyaltyPoints points) {
}
