package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.LoyaltyPoints;

public record AddLoyaltyPointsCommand(CustomerId id, LoyaltyPoints points) {
}
