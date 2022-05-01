package me.zhengjie.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Converter
public class StringSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(final Set<String> attribute) {
        return attribute == null || attribute.isEmpty() ? null :
                String.join("\n", attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(final String data) {
        return data == null || data.isBlank() ?
                null :
                new LinkedHashSet<>(Arrays.asList(data.split("\n")));
    }
}
