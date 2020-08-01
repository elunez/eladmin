/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.DataPermission;
import me.zhengjie.annotation.Query;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked", "all"})
public class QueryHelp {
    public static ThreadLocal<QueryWrapper> queryWrapperThreadLocal = new ThreadLocal<>();
    protected static Map<String, List<Field>> FIELD_CACHE = new ConcurrentHashMap<>(16);
    protected static Map<String, ElField> COLUMN_CACHE = new ConcurrentHashMap<>(16);

    /**
     * JPA 查询参数构建
     *
     * @param root
     * @param query
     * @param cb
     * @param <R>
     * @param <Q>
     * @return /
     */
    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        /**
         * MyvatisPlus 使用
         */
        if (query == null) {
            return cb.and(list.toArray(new Predicate[0]));
        }
        // 数据权限验证
        DataPermission permission = query.getClass().getAnnotation(DataPermission.class);
        if (permission != null) {
            // 获取数据权限
            List<Long> dataScopes = SecurityUtils.getCurrentUserDataScope();
            if (CollectionUtil.isNotEmpty(dataScopes)) {
                if (StringUtils.isNotBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName())) {
                    Join join = root.join(permission.joinName(), JoinType.LEFT);
                    list.add(getExpression(permission.fieldName(), join, root).in(dataScopes));
                } else if (StringUtils.isBlank(permission.joinName()) && StringUtils.isNotBlank(permission.fieldName())) {
                    list.add(getExpression(permission.fieldName(), null, root).in(dataScopes));
                }
            }
        }
        try {
            List<Field> fields = getAllFields(query.getClass());
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                // 设置对象的访问权限，保证对private的属性的访
                field.setAccessible(true);
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    String propName = q.propName();
                    String joinName = q.joinName();
                    String blurry = q.blurry();
                    String attributeName = isBlank(propName) ? field.getName() : propName;
                    Class<?> fieldType = field.getType();
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    Join join = null;
                    // 模糊多字段
                    if (ObjectUtil.isNotEmpty(blurry)) {
                        String[] blurrys = blurry.split(",");
                        List<Predicate> orPredicate = new ArrayList<>();
                        for (String s : blurrys) {
                            final Path<Object> column = root.get(s);
                            orPredicate.add(cb.like(column
                                    .as(String.class), "%" + val.toString() + "%"));
                        }
                        Predicate[] p = new Predicate[orPredicate.size()];
                        list.add(cb.or(orPredicate.toArray(p)));
                        continue;
                    }
                    if (ObjectUtil.isNotEmpty(joinName)) {
                        String[] joinNames = joinName.split(">");
                        for (String name : joinNames) {
                            switch (q.join()) {
                                case LEFT:
                                    if (ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)) {
                                        join = join.join(name, JoinType.LEFT);
                                    } else {
                                        join = root.join(name, JoinType.LEFT);
                                    }
                                    break;
                                case RIGHT:
                                    if (ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)) {
                                        join = join.join(name, JoinType.RIGHT);
                                    } else {
                                        join = root.join(name, JoinType.RIGHT);
                                    }
                                    break;
                                case INNER:
                                    if (ObjectUtil.isNotNull(join) && ObjectUtil.isNotNull(val)) {
                                        join = join.join(name, JoinType.INNER);
                                    } else {
                                        join = root.join(name, JoinType.INNER);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    switch (q.type()) {
                        case EQUAL:
                            list.add(cb.equal(getExpression(attributeName, join, root)
                                    .as((Class<? extends Comparable>) fieldType), val));
                            break;
                        case GREATER_THAN:
                            list.add(cb.greaterThanOrEqualTo(getExpression(attributeName, join, root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN:
                            list.add(cb.lessThanOrEqualTo(getExpression(attributeName, join, root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case LESS_THAN_NQ:
                            list.add(cb.lessThan(getExpression(attributeName, join, root)
                                    .as((Class<? extends Comparable>) fieldType), (Comparable) val));
                            break;
                        case INNER_LIKE:
                            list.add(cb.like(getExpression(attributeName, join, root)
                                    .as(String.class), "%" + val.toString() + "%"));
                            break;
                        case LEFT_LIKE:
                            list.add(cb.like(getExpression(attributeName, join, root)
                                    .as(String.class), "%" + val.toString()));
                            break;
                        case RIGHT_LIKE:
                            list.add(cb.like(getExpression(attributeName, join, root)
                                    .as(String.class), val.toString() + "%"));
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>) val)) {
                                list.add(getExpression(attributeName, join, root).in((Collection<Long>) val));
                            }
                            break;
                        case NOT_EQUAL:
                            list.add(cb.notEqual(getExpression(attributeName, join, root), val));
                            break;
                        case NOT_NULL:
                            list.add(cb.isNotNull(getExpression(attributeName, join, root)));
                            break;
                        case IS_NULL:
                            list.add(cb.isNull(getExpression(attributeName, join, root)));
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>) val);
                            list.add(cb.between(getExpression(attributeName, join, root).as((Class<? extends Comparable>) between.get(0).getClass()),
                                    (Comparable) between.get(0), (Comparable) between.get(1)));
                            break;
                        default:
                            break;
                    }
                }
                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int size = list.size();
        return cb.and(list.toArray(new Predicate[size]));
    }

    /**
     * Mybatis Plus  查询构建
     *
     * @param criteria
     * @param clazz
     * @param <K>
     * @return /
     */
    public static <K> QueryWrapper<K> getQueryWrapper(Object criteria, Class clazz) {
        QueryWrapper<K> queryWrapper = Wrappers.query();
        final List<Field> allFields = getAllFields(criteria.getClass());
        allFields.forEach(field -> {
            buildQuery(criteria, field, clazz, queryWrapper);
        });
        return queryWrapper;
    }

    private static <K> void buildQuery(Object criteria, Field field, Class clazz, QueryWrapper<K> queryWrapper) {
        final Query query = field.getAnnotation(Query.class);
        Object value = null;
        final TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        boolean accessible = field.isAccessible();
        String attributeName = null;
        try {
            // 设置对象的访问权限，保证对private的属性的访
            field.setAccessible(true);
            value = field.get(criteria);
            if (Objects.isNull(value)) {
                return;
            }
            attributeName = getTableColumnFromField(tableInfo, clazz.getName(), field.getName());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return;
        } finally {
            field.setAccessible(accessible);
        }
        if (Objects.nonNull(query)) {
            String propName = query.propName();
            String blurry = query.blurry();
            attributeName = isBlank(propName) ? attributeName : propName;
            if (StringUtils.isNotBlank(blurry)) {
                String[] blurrys = blurry.split(",");
                for (String item : blurrys) {
                    queryWrapper.like(item, value);
                }
            }

            List<Object> between = null;
            switch (query.type()) {
                case EQUAL:
                    break;
                case GREATER_THAN:
                    queryWrapper.ge(attributeName, value);
                    break;
                case LESS_THAN:
                    queryWrapper.le(attributeName, value);
                    break;
                case LESS_THAN_NQ:
                    queryWrapper.lt(attributeName, value);
                    break;
                case INNER_LIKE:
                    queryWrapper.like(attributeName, value);
                    break;
                case LEFT_LIKE:
                    queryWrapper.likeLeft(attributeName, value);
                    break;
                case RIGHT_LIKE:
                    queryWrapper.likeRight(attributeName, value);
                    break;
                case IN:
                    between = new ArrayList<>((List<Object>) value);
                    queryWrapper.in(attributeName, between.toArray());
                    break;
                case NOT_EQUAL:
                    queryWrapper.ne(attributeName, value);
                    break;
                case NOT_NULL:
                    queryWrapper.isNotNull(attributeName);
                    break;
                case IS_NULL:
                    queryWrapper.isNull(attributeName);
                    break;
                case BETWEEN:
                    between = new ArrayList<>((List<Object>) value);
                    queryWrapper.in(attributeName, between.toArray());
                    break;
                default:
                    break;
            }
        } else {
            queryWrapper.eq(field.getName(), value);
        }
    }


    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if (ObjectUtil.isNotEmpty(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
    }

    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 依据Mybatis Plus 获取 Database 真实Column 字段
     *
     * @param tableInfo
     * @param field
     * @return /
     */
    public static String getTableColumnFromField(TableInfo tableInfo, String className, String field) {
        String columnName = null;
        final String key = className + "_" + field;
        if (FIELD_CACHE.containsKey(key)) {
            final ElField elField = COLUMN_CACHE.get(field);
            if (elField.isStatus()) {
                columnName = elField.getColumn();
            }
        } else {
            AtomicReference<String> tableColumnName = null;
            final String name = field;
            for (TableFieldInfo item : tableInfo.getFieldList()) {
                if (item.getField().getName().equals(name)) {
                    tableColumnName = new AtomicReference<>();
                    tableColumnName.set(item.getColumn());
                    break;
                }
            }
            if (Objects.nonNull(tableColumnName)) {
                columnName = tableColumnName.get();
                COLUMN_CACHE.put(key, new ElField(columnName, true));
            } else {
                COLUMN_CACHE.put(key, new ElField(columnName, false));
            }
        }
        return columnName;
    }

    public static List<Field> getAllFields(Class clazz) {
//        @Todo 过滤无关字段
        List<Field> fields = null;
        if (clazz != null) {
            if (FIELD_CACHE.containsKey(clazz.getName())) {
                fields = FIELD_CACHE.get(clazz.getName());
            } else {
                fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
                final List<Field> allFields = getAllFields(clazz.getSuperclass());
                if (CollUtil.isNotEmpty(allFields)) {
                    fields.addAll(allFields);
                }
                FIELD_CACHE.put(clazz.getName(), fields);
            }
        }
        return fields;
    }
}

/**
 * @author liaojinlong
 * @since 2020/7/6 21:04
 */
class ElField {
    private String column;
    private boolean status;

    public ElField(String column, boolean status) {
        this.column = column;
        this.status = status;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
