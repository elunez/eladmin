package me.zhengjie.exception;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String field, String val) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(field, val)));
    }

    private static String generateMessage(String entity, Map<Object, Object> searchParams) {
        return StringUtils.capitalize(entity) +
                " 不存在 " +
                searchParams;
    }

    private static Map<Object, Object> toMap(String... entries) {
        return new HashMap<Object, Object>(){{ put(entries[0], entries[1]); }};
    }
}