package me.zhengjie.converter;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface EnumSetConverter<T extends Enum<T>> extends AttributeConverter<Set<T>, String> {

    Class<T> enumClass();

    @Override
    default String convertToDatabaseColumn(final Set<T> attribute) {
        return attribute == null || attribute.isEmpty() ?
                null :
                attribute.stream().map(T::name)
                        .sorted()
                        .collect(Collectors.joining(","));
    }

    @Override
    default Set<T> convertToEntityAttribute(final String data) {
        final var values = Arrays.stream(enumClass().getEnumConstants()).map(T::name).collect(Collectors.toSet());
        return data == null || data.isBlank() ?
                null :
                new LinkedHashSet<T>(Stream.of(data.split(","))
                        .map(String::trim)
                        .filter(values::contains)
                        .map(name -> T.valueOf(enumClass(), name))
                        .collect(Collectors.toList()));
    }
}
