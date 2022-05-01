package me.zhengjie.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(final List<String> attribute) {
        return attribute == null || attribute.isEmpty() ? null :
                String.join("\n", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(final String data) {
        return data == null || data.isBlank() ?
                null :
                Arrays.asList(data.split("\n"));
    }
}
