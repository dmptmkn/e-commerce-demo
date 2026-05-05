package com.example.infrastructure.persistence.converter;

import com.example.domain.CustomerName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerNameConverter implements AttributeConverter<CustomerName, String> {

    @Override
    public String convertToDatabaseColumn(CustomerName attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public CustomerName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CustomerName.of(dbData);
    }
}
