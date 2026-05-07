package com.example.application.command;

import com.example.domain.CustomerId;
import com.example.domain.LoyaltyPoints;
import jakarta.validation.constraints.NotNull;

public record SubtractLoyaltyPointsCommand(@NotNull CustomerId id, @NotNull LoyaltyPoints points) {
}
