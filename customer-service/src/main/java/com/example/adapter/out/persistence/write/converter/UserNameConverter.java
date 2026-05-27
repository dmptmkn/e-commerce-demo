package com.example.adapter.out.persistence.write.converter;

import com.example.core.domain.valueobject.UserName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserNameConverter implements AttributeConverter<UserName, String> {

    @Override
    public String convertToDatabaseColumn(UserName attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public UserName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : UserName.of(dbData);
    }
}
