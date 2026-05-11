package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.LoyaltyPoints;

public record SubtractLoyaltyPointsCommand(CustomerId id, LoyaltyPoints points) {
}
