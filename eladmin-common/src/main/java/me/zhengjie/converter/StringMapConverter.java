package me.zhengjie.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

@Converter
public class StringMapConverter implements AttributeConverter<Map<String, String>, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(final Map<String, String> value) {
        try {
            return value == null ? null : OBJECT_MAPPER.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(final String value) {
        try {
            final var type = new TypeReference<Map<String, String>>() {
            };
            return value == null ? null : OBJECT_MAPPER.readValue(value, type);
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
