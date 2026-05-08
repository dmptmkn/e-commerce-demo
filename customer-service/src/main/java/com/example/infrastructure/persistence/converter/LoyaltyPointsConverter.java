package com.example.infrastructure.persistence.converter;

import com.example.domain.LoyaltyPoints;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LoyaltyPointsConverter implements AttributeConverter<LoyaltyPoints, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LoyaltyPoints attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public LoyaltyPoints convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : LoyaltyPoints.of(dbData);
    }
}
