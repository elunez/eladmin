package me.zhengjie.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.zhengjie.annotation.PredicateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @描述 :  JavaBean 帮助
 * @作者 :  Dong ZhaoYang
 * @日期 :  2016/12/01
 * @时间 :  09:54
 */
@SuppressWarnings("unused")
public class BeanHelp {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(BeanHelp.class);

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * @param <C>    泛型
     * @param object 对象
     * @return 属性集合
     * @描述 : 获取对象的JSON属性Map对象集合
     * @作者 : Dong ZhaoYang
     * @日期 : 2017/02/24
     * @时间 : 11:12
     */
    @SuppressWarnings("unchecked")
    public static <C> Map<String, C> getBeanJSONPropertyMap(Object object, Class<C> clazz) {
        Map<String, C> result = new LinkedHashMap<>();
        try {
            JavaType javaType = objectMapper.getTypeFactory()
                    .constructParametrizedType(Map.class, Map.class, String.class, clazz);
            result = objectMapper.readValue(objectMapper.writeValueAsString(object), javaType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param <C>      泛型
     * @param propName 属性名
     * @param object   对象
     * @param clazz    返回类型
     * @return 对象的属性名
     * @描述 : 获取请求对象的JSON属性的Map集合
     * @作者 : Dong ZhaoYang
     * @日期 : 2017/03/20
     * @时间 : 12:47
     */
    @SuppressWarnings("unchecked")
    private static <C> Map<String, C> getBeanJSONPropertyMap(String propName, Object object, Class<C> clazz) {
        Map<String, C> result = new LinkedHashMap<>();
        try {
            Class<?> oClass = object.getClass();
            Field[] fields = oClass.getDeclaredFields();
            fieldLoop:
            for (Field field : fields) {
                String name = field.getName();
                JsonIgnore ignore = oClass.getDeclaredField(name).getAnnotation(JsonIgnore.class);
                if (ignore != null) {
                    continue;
                }
                JsonProperty property = oClass.getDeclaredField(name).getAnnotation(JsonProperty.class);
                if (property != null) {
                    name = property.value();
                }
                name = propName == null ? name : propName + "." + name;
                field.setAccessible(true);
                Object prop = field.get(object);
                if (prop == null) {
                    continue;
                }
                if (PropertyType.isBasicClazz(field.getType()) || prop instanceof Class
                        || prop instanceof Collection) {
                    result.put(name, (C) prop);
                } else if (prop instanceof Enum) {
                    Method[] methods = prop.getClass().getDeclaredMethods();
                    for (Method method : methods) {
                        JsonValue jsonValue = method.getAnnotation(JsonValue.class);
                        if (jsonValue != null) {
                            Object enumVal = method.invoke(prop);
                            result.putAll(getBeanJSONPropertyMap(name, enumVal, clazz));
                            continue fieldLoop;
                        }
                    }
                } else {
                    result.putAll(getBeanJSONPropertyMap(name, prop, clazz));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param <T>             泛型
     * @param json            json字符串
     * @param parentClass     最外层的父类
     * @param childrenClasses 属性类
     * @return 反序列化后的对象
     * @描述 : 从JSON中获取对象
     * @作者 : Dong ZhaoYang
     * @日期 : 2017/02/24
     * @时间 : 11:45
     */
    public static <T> T getBeanFromJson(String json,
                                        Class<?> parentClass, Class<?>... childrenClasses) {
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametrizedType(parentClass, parentClass, childrenClasses);
        T o = null;
        try {
            o = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return o;
    }

    /**
     * @param clazz 需要实例化的字节码对象
     * @param <T>   对象类型
     * @return 对象实例
     * @描述 :  获取clazz的初始化对象，所有支持的属性 {@link PropertyType}将设为
     * 对应的非null默认值
     * @作者 :	Dong ZhaoYang
     * @日期 :	2017/3/6
     * @时间 :	15:07
     */
    public static <T> T getNoEmptyPropertyInstance(Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (writeMethod != null) {
                    writeMethod.invoke(instance,
                            PropertyType.fromClazz(propertyDescriptor.getPropertyType()).getDefaultValue());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instance;
    }

    /**
     * @param origin 原始对象数据
     * @param update 更新对象数据
     * @param <T>    对象类型
     * @return 待更新的对象
     * @描述 :  如果update的属性不为空，则更新对象origin的属性
     * @作者 :  Dong ZhaoYang
     * @日期 :  2017/8/7
     * @时间 :  15:48
     */
    @SuppressWarnings("unchecked")
    public static <T> T updateEntityExceptEmptyProps(T origin, T update) {
        T instance = null;
        try {
            Class<T> clazz = (Class<T>) origin.getClass();
            instance = clazz.newInstance();
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Method writeMethod = propertyDescriptor.getWriteMethod();
                if (readMethod != null && writeMethod != null) {
                    Object originProperty = readMethod.invoke(origin);
                    Object updateProperty = readMethod.invoke(update);
                    writeMethod.invoke(instance, NullHelp.isNull(updateProperty) ? originProperty : updateProperty);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return instance;
    }

    /**
     * @描述 :  转换为Predicate
     * @作者 :  Dong ZhaoYang
     * @日期 :  2017/8/7
     * @时间 :  17:25
     */
    @SuppressWarnings("unchecked")
    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if(query == null){
            return cb.and(list.toArray(new Predicate[list.size()]));
        }
        try {
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                PredicateInfo predicateInfo = field.getAnnotation(PredicateInfo.class);
                if (predicateInfo != null) {
                    String propName = predicateInfo.propName();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (NullHelp.isNull(val)) {
                        continue;
                    }
                    switch (predicateInfo.queryType()) {
                        case BASIC:
                            list.add(cb.equal(root.get(attributeName).as(fieldType), val));
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThanOrEqualTo(root.get(attributeName)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN:
                            list.add(cb.lessThanOrEqualTo(root.get(attributeName)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN_NQ:
                            list.add(cb.lessThan(root.get(attributeName)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case INNER_LIKE:
                            list.add(cb.like(root.get(attributeName)
                                    .as(String.class), "%" + val.toString() + "%"));
                            break;
                        case LEFT_LIKE:
                            list.add(cb.like(root.get(attributeName)
                                    .as(String.class), "%" + val.toString()));
                            break;
                        case RIGHT_LIKE:
                            list.add(cb.like(root.get(attributeName)
                                    .as(String.class), val.toString() + "%"));
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cb.and(list.toArray(new Predicate[list.size()]));
    }

    private static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * @param propName 属性名
     * @param object   目标实例对象
     * @param <T>      对象类型
     * @return 属性值
     * @描述 :  获取object对象的propName的属性值
     * @作者 :	Dong ZhaoYang
     * @日期 :	2017/3/6
     * @时间 :	15:08
     */
    public static <T> Object getProperty(String propName, T object) {
        Object result = null;
        try {
            Method readMethod = new PropertyDescriptor(propName, object.getClass()).getReadMethod();
            if (readMethod != null) {
                result = readMethod.invoke(object);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * @描述 : check 属性是否为空，有一个为空则返回false
     * @作者 : lww
     * @日期 : 2017/3/23
     * @时间 : 16:14
     */
    public static <T> boolean checkProperties(T object) {
        T instance = null;
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method readMethod = propertyDescriptor.getReadMethod();
                Object result = readMethod.invoke(object);
                if (result == null) {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    /**
     * @param object 目标实例对象
     * @param <T>    对象类型
     * @param <C>    属性类型
     * @return 属性值
     * @描述 :  获取object对象的propertyClazz的属性值
     * @作者 :	Dong ZhaoYang
     * @日期 :	2017/3/6
     * @时间 :	15:08
     */
    @SuppressWarnings("unchecked")
    public static <T, C> C getProperty(T object, Class<C> propertyClazz) {
        C result = null;
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getPropertyType().equals(propertyClazz)) {
                    Method readMethod = propertyDescriptor.getReadMethod();
                    if (readMethod != null) {
                        result = (C) readMethod.invoke(object);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param clazz         拥有将获取属性的类
     * @param propertyClazz 属性的类
     * @return 属性的名称
     * @描述 : 获取属性名
     * @作者 : Dong ZhaoYang
     * @日期 : 2017/03/14
     * @时间 : 13:33
     */
    public static String getPropertyName(Class clazz, Class propertyClazz) {
        String result = null;
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getPropertyType().equals(propertyClazz)) {
                    result = propertyDescriptor.getName();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @描述 :  Bean属性的字节码类型及其对应的默认值
     * @作者 :	Dong ZhaoYang
     * @日期 :	2017/3/6
     * @时间 :	15:08
     */
    public enum PropertyType {

        BOX_BOOLEAN(Boolean.class, Boolean.FALSE),
        BOX_BYTE(Byte.class, 0),
        BOX_SHORT(Short.class, 0),
        BOX_CHARACTER(Character.class, '\u0000'),
        BOX_INTEGER(Integer.class, 0),
        BOX_LONG(Long.class, 0L),
        BOX_FLOAT(Float.class, 0.00F),
        BOX_DOUBLE(Double.class, 0.00D),
        STRING(String.class, ""),
        BIG_DECIMAL(BigDecimal.class, BigDecimal.ZERO),
        BIG_INTEGER(BigInteger.class, BigInteger.ZERO),
        DATE(Date.class, new Date()),
        NULL(PropertyType.class, null);

        private Class clazz;
        private Object defaultValue;

        PropertyType(Class clazz, Object defaultValue) {
            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }

        public static boolean isBoxPrimitive(Class clazz) {
            switch (clazz.getName()) {
                case "java.lang.Boolean":
                case "java.lang.Byte":
                case "java.lang.Short":
                case "java.lang.Character":
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Float":
                case "java.lang.Double":
                case "java.lang.String":
                    return true;
                default:
                    return false;
            }
        }

        public static boolean isBasicClazz(Class clazz) {
            switch (clazz.getName()) {
                case "java.lang.Boolean":
                case "java.lang.Byte":
                case "java.lang.Short":
                case "java.lang.Character":
                case "java.lang.Integer":
                case "java.lang.Long":
                case "java.lang.Float":
                case "java.lang.Double":
                case "java.lang.String":
                case "java.math.BigDecimal":
                case "java.math.BigInteger":
                case "java.util.Date":
                    return true;
                default:
                    return false;
            }
        }

        public static PropertyType fromClazz(Class clazz) {
            switch (clazz.getName()) {
                case "java.lang.Boolean":
                    return BOX_BOOLEAN;
                case "java.lang.Byte":
                    return BOX_BYTE;
                case "java.lang.Short":
                    return BOX_SHORT;
                case "java.lang.Character":
                    return BOX_CHARACTER;
                case "java.lang.Integer":
                    return BOX_INTEGER;
                case "java.lang.Long":
                    return BOX_LONG;
                case "java.lang.Float":
                    return BOX_FLOAT;
                case "java.lang.Double":
                    return BOX_DOUBLE;
                case "java.lang.String":
                    return STRING;
                case "java.math.BigDecimal":
                    return BIG_DECIMAL;
                case "java.math.BigInteger":
                    return BIG_INTEGER;
                case "java.util.Date":
                    return DATE;
                default:
                    return NULL;
            }
        }

        public Class getClazz() {
            return clazz;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        @Override
        public String toString() {
            return "PropertyType{" +
                    "clazz=" + clazz +
                    ", defaultValue=" + defaultValue +
                    "} " + super.toString();
        }

    }

    public static List<Field> getParentField(Class pc) {
        List<Field> list = null;
        while (pc != null) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.addAll(Arrays.asList(pc.getDeclaredFields()));
            pc = pc.getSuperclass();
        }
        return list;
    }

    public static List<Field> getParentField(Object o) {
        List<Field> list = null;
        Class pc = o.getClass();
        return BeanHelp.getParentField(pc);
    }
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

}
