///*
// * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License. You may obtain a copy of
// * the License at
// * <p>
// * https://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
//package com.baomidou.mybatisplus.core.metadata;
//
//import com.baomidou.mybatisplus.annotation.*;
//import com.baomidou.mybatisplus.annotation.impl.TableFieldImp;
//import com.baomidou.mybatisplus.annotation.impl.TableIdImp;
//import com.baomidou.mybatisplus.core.MybatisPlusVersion;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
//import com.baomidou.mybatisplus.core.toolkit.*;
//import org.apache.ibatis.builder.MapperBuilderAssistant;
//import org.apache.ibatis.builder.StaticSqlSource;
//import org.apache.ibatis.executor.keygen.KeyGenerator;
//import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
//import org.apache.ibatis.logging.Log;
//import org.apache.ibatis.logging.LogFactory;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ResultMap;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.reflection.Reflector;
//import org.apache.ibatis.reflection.ReflectorFactory;
//import org.apache.ibatis.session.Configuration;
//
//import javax.persistence.*;
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static java.util.stream.Collectors.toList;
//
///**
// * <p>
// * 实体类反射表辅助类
// * </p>
// *
// * @author hubin sjy
// * @since 2016-09-09
// */
//public class TableInfoHelperBak {
//
//    private static final Log logger = LogFactory.getLog(TableInfoHelper.class);
//
//    /**
//     * 储存反射类表信息
//     */
//    private static final Map<Class<?>, TableInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();
//
//    /**
//     * 默认表主键名称
//     */
//    private static final String DEFAULT_ID_NAME = "id";
//
//    /**
//     * <p>
//     * 获取实体映射表信息
//     * </p>
//     *
//     * @param clazz 反射实体类
//     * @return 数据库表反射信息
//     */
//    public static TableInfo getTableInfo(Class<?> clazz) {
//        if (clazz == null
//                || ReflectionKit.isPrimitiveOrWrapper(clazz)
//                || clazz == String.class) {
//            return null;
//        }
//        // https://github.com/baomidou/mybatis-plus/issues/299
//        TableInfo tableInfo = TABLE_INFO_CACHE.get(ClassUtils.getUserClass(clazz));
//        if (null != tableInfo) {
//            return tableInfo;
//        }
//        //尝试获取父类缓存
//        Class<?> currentClass = clazz;
//        while (null == tableInfo && Object.class != currentClass) {
//            currentClass = currentClass.getSuperclass();
//            tableInfo = TABLE_INFO_CACHE.get(ClassUtils.getUserClass(currentClass));
//        }
//        if (tableInfo != null) {
//            TABLE_INFO_CACHE.put(ClassUtils.getUserClass(clazz), tableInfo);
//        }
//        return tableInfo;
//    }
//
//    /**
//     * <p>
//     * 获取所有实体映射表信息
//     * </p>
//     *
//     * @return 数据库表反射信息集合
//     */
//    @SuppressWarnings("unused")
//    public static List<TableInfo> getTableInfos() {
//        return Collections.unmodifiableList(new ArrayList<>(TABLE_INFO_CACHE.values()));
//    }
//
//    /**
//     * <p>
//     * 实体类反射获取表信息【初始化】
//     * </p>
//     *
//     * @param clazz 反射实体类
//     * @return 数据库表反射信息
//     */
//    public synchronized static TableInfo initTableInfo(MapperBuilderAssistant builderAssistant, Class<?> clazz) {
//        return TABLE_INFO_CACHE.computeIfAbsent(clazz, (key) -> {
//            /* 没有获取到缓存信息,则初始化 */
//            TableInfo tableInfo = new TableInfo(key);
//            GlobalConfig globalConfig;
//            if (null != builderAssistant) {
//                tableInfo.setCurrentNamespace(builderAssistant.getCurrentNamespace());
//                tableInfo.setConfiguration(builderAssistant.getConfiguration());
//                globalConfig = GlobalConfigUtils.getGlobalConfig(builderAssistant.getConfiguration());
//            } else {
//                // 兼容测试场景
//                globalConfig = GlobalConfigUtils.defaults();
//            }
//            if (globalConfig.isBanner()) {
//                System.out.println("MyBatis Plus (Jpa Patch)");
//            }
//            /* 初始化表名相关 */
//            final String[] excludeProperty = initTableName(key, globalConfig, tableInfo);
//            globalConfig.setBanner(false);
//
//            List<String> excludePropertyList = excludeProperty != null && excludeProperty.length > 0 ? Arrays.asList(excludeProperty) : Collections.emptyList();
//            globalConfig.setBanner(false);
//
//            /* 初始化字段相关 */
//            initTableFields(key, globalConfig, tableInfo, excludePropertyList);
//            globalConfig.setBanner(false);
//
//            /* 缓存 lambda */
//            LambdaUtils.installCache(tableInfo);
//
//            /* 自动构建 resultMap */
//            tableInfo.initResultMapIfNeed();
//
//            return tableInfo;
//        });
//    }
//
//    /**
//     * <p>
//     * 初始化 表数据库类型,表名,resultMap
//     * </p>
//     *
//     * @param clazz        实体类
//     * @param globalConfig 全局配置
//     * @param tableInfo    数据库表反射信息
//     * @return 需要排除的字段名
//     */
//    private static String[] initTableName(Class<?> clazz, GlobalConfig globalConfig, TableInfo tableInfo) {
//        /* 数据库全局配置 */
//        GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
//        /**
//         * JPA 注解
//         */
//        final Table jpaTableAnnotation = clazz.getAnnotation(Table.class);
//        /**
//         * MP 注解
//         */
//        final TableName mpTableAnnotation = clazz.getAnnotation(TableName.class);
//
//        String tableName = clazz.getSimpleName();
//        String tablePrefix = dbConfig.getTablePrefix();
//        String schema = dbConfig.getSchema();
//        boolean tablePrefixEffect = true;
//        String[] excludeProperty = null;
//        /**
//         * JPA 注解优先加载，后期 MP 可以覆盖
//         */
//        if (Objects.nonNull(jpaTableAnnotation)) {
//            if (StringUtils.isNotBlank(jpaTableAnnotation.name())) {
//                tableName = jpaTableAnnotation.name();
//            } else {
//                tableName = initTableNameWithDbConfig(tableName, dbConfig);
//            }
//            if (StringUtils.isNotBlank(jpaTableAnnotation.schema())) {
//                schema = jpaTableAnnotation.schema();
//            }
//        }
//        /**
//         * MP table 注解
//         */
//        if (mpTableAnnotation != null) {
//            if (StringUtils.isNotBlank(mpTableAnnotation.value())) {
//                tableName = mpTableAnnotation.value();
//                if (StringUtils.isNotBlank(tablePrefix) && !mpTableAnnotation.keepGlobalPrefix()) {
//                    tablePrefixEffect = false;
//                }
//            } else if (Objects.isNull(jpaTableAnnotation)) {
//                tableName = initTableNameWithDbConfig(tableName, dbConfig);
//            }
//            if (StringUtils.isNotBlank(mpTableAnnotation.schema())) {
//                schema = mpTableAnnotation.schema();
//            }
//            /* 表结果集映射 */
//            if (StringUtils.isNotBlank(mpTableAnnotation.resultMap())) {
//                tableInfo.setResultMap(mpTableAnnotation.resultMap());
//            }
//            tableInfo.setAutoInitResultMap(mpTableAnnotation.autoResultMap());
//            excludeProperty = mpTableAnnotation.excludeProperty();
//        } else if (Objects.isNull(jpaTableAnnotation)) {
//            tableName = initTableNameWithDbConfig(tableName, dbConfig);
//        }
//
//        String targetTableName = tableName;
//        if (StringUtils.isNotBlank(tablePrefix) && tablePrefixEffect) {
//            targetTableName = tablePrefix + targetTableName;
//        }
//        if (StringUtils.isNotBlank(schema)) {
//            targetTableName = schema + StringPool.DOT + targetTableName;
//        }
//
//        tableInfo.setTableName(targetTableName);
//
//        /* 开启了自定义 KEY 生成器 */
//        if (null != dbConfig.getKeyGenerator()) {
//            tableInfo.setKeySequence(clazz.getAnnotation(KeySequence.class));
//        }
//        return excludeProperty;
//    }
//
//    /**
//     * 根据 DbConfig 初始化 表名
//     *
//     * @param className 类名
//     * @param dbConfig  DbConfig
//     * @return 表名
//     */
//    private static String initTableNameWithDbConfig(String className, GlobalConfig.DbConfig dbConfig) {
//        String tableName = className;
//        // 开启表名下划线申明
//        if (dbConfig.isTableUnderline()) {
//            tableName = StringUtils.camelToUnderline(tableName);
//        }
//        // 大写命名判断
//        if (dbConfig.isCapitalMode()) {
//            tableName = tableName.toUpperCase();
//        } else {
//            // 首字母小写
//            tableName = StringUtils.firstToLowerCase(tableName);
//        }
//        return tableName;
//    }
//
//    /**
//     * <p>
//     * 初始化 表主键,表字段
//     * </p>
//     *
//     * @param clazz        实体类
//     * @param globalConfig 全局配置
//     * @param tableInfo    数据库表反射信息
//     */
//    public static void initTableFields(Class<?> clazz, GlobalConfig globalConfig, TableInfo tableInfo, List<String> excludeProperty) {
//        /* 数据库全局配置 */
//        GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
//        ReflectorFactory reflectorFactory = tableInfo.getConfiguration().getReflectorFactory();
//        //TODO @咩咩 有空一起来撸完这反射模块.
//        Reflector reflector = reflectorFactory.findForClass(clazz);
//        List<Field> list = getAllFields(clazz);
//        // 标记是否读取到主键
//        boolean isReadPK = false;
//        /**
//         * 使用JPA 初始化
//         */
//        boolean jpaReadPK = false;
//        // 是否存在 @TableId 注解
//        boolean existTableId = isExistTableId(list);
//
//        List<TableFieldInfo> fieldList = new ArrayList<>(list.size());
//        for (Field field : list) {
//            /**
//             * 手动排除或者使用 transient注解的 排除
//             */
//            final boolean exclude = excludeProperty.contains(field.getName());
//            final boolean transientField = Objects.nonNull(field.getAnnotation(Transient.class));
//            if (exclude || transientField) {
//                continue;
//            }
//
//            /* 主键ID 初始化 */
//            if (existTableId) {
//                Id id = field.getAnnotation(Id.class);
//                TableId tableId = field.getAnnotation(TableId.class);
//                if (tableId != null || id != null) {
//                    if (isReadPK) {
//                        if (jpaReadPK) {
//                            throw ExceptionUtils.mpe("JPA @Id has been Init: \"%s\".", clazz.getName());
//                        } else {
//                            throw ExceptionUtils.mpe("@TableId can't more than one in Class: \"%s\".", clazz.getName());
//                        }
//                    } else {
//                        if (Objects.isNull(tableId)) {
//                            TableIdImp tableIdImp = new TableIdImp();
//                            Column column = field.getAnnotation(Column.class);
//                            if (Objects.nonNull(column)) {
//                                tableIdImp.setValue(column.name());
//                            }
//                            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
//                            if (Objects.nonNull(generatedValue)) {
//                                tableIdImp.setType(IdType.AUTO);
//                                logger.warn("JPA compatible mode, Automatic growth is the only way to generate primary key");
//                            }
//                            tableId = tableIdImp;
//                            jpaReadPK = true;
//
//                        }
//                        initTableIdWithAnnotation(dbConfig, tableInfo, field, tableId, reflector);
//                        isReadPK = true;
//                        continue;
//                    }
//                }
//            } else if (!isReadPK) {
//                isReadPK = initTableIdWithoutAnnotation(dbConfig, tableInfo, field, reflector);
//                if (isReadPK) {
//                    continue;
//                }
//            }
//            TableField tableField = field.getAnnotation(TableField.class);
//
//            /* 有 @TableField 注解的字段初始化 */
//            if (tableField != null) {
//                fieldList.add(new TableFieldInfo(dbConfig, tableInfo, field, tableField, reflector));
//                continue;
//            } else {
//                final Column column = field.getAnnotation(Column.class);
//                if (Objects.nonNull(column)) {
//                    TableFieldImp tableFieldImp = new TableFieldImp();
//                    tableFieldImp.setValue(column.name());
//                    tableField = tableFieldImp;
//                    fieldList.add(new TableFieldInfo(dbConfig, tableInfo, field, tableField, reflector));
//                    continue;
//                }
//            }
//
//            /* 无 @TableField 注解的字段初始化 */
//            fieldList.add(new TableFieldInfo(dbConfig, tableInfo, field, reflector));
//        }
//
//        /* 检查逻辑删除字段只能有最多一个 */
//        Assert.isTrue(fieldList.parallelStream().filter(TableFieldInfo::isLogicDelete).count() < 2L,
//                String.format("@TableLogic can't more than one in Class: \"%s\".", clazz.getName()));
//
//        /* 字段列表,不可变集合 */
//        tableInfo.setFieldList(Collections.unmodifiableList(fieldList));
//
//        /* 未发现主键注解，提示警告信息 */
//        if (!isReadPK) {
//            logger.warn(String.format("Can not find table primary key in Class: \"%s\".", clazz.getName()));
//        }
//    }
//
//    /**
//     * <p>
//     * 判断主键注解是否存在
//     * </p>
//     *
//     * @param list 字段列表
//     * @return true 为存在 @TableId 注解;
//     */
//    public static boolean isExistTableId(List<Field> list) {
//        return list.stream().anyMatch(field ->
//                field.isAnnotationPresent(TableId.class) ||
//                        field.isAnnotationPresent(Id.class));
//    }
//
//    /**
//     * <p>
//     * 主键属性初始化
//     * </p>
//     *
//     * @param dbConfig  全局配置信息
//     * @param tableInfo 表信息
//     * @param field     字段
//     * @param tableId   注解
//     * @param reflector Reflector
//     */
//    private static void initTableIdWithAnnotation(GlobalConfig.DbConfig dbConfig, TableInfo tableInfo,
//                                                  Field field, TableId tableId, Reflector reflector) {
//        boolean underCamel = tableInfo.isUnderCamel();
//        final String property = field.getName();
//        if (field.getAnnotation(TableField.class) != null) {
//            logger.warn(String.format("This \"%s\" is the table primary key by @TableId annotation in Class: \"%s\",So @TableField annotation will not work!",
//                    property, tableInfo.getEntityType().getName()));
//        }
//        /* 主键策略（ 注解 > 全局 ） */
//        // 设置 Sequence 其他策略无效
//        if (IdType.NONE == tableId.type()) {
//            tableInfo.setIdType(dbConfig.getIdType());
//        } else {
//            tableInfo.setIdType(tableId.type());
//        }
//
//        /* 字段 */
//        String column = property;
//        if (StringUtils.isNotBlank(tableId.value())) {
//            column = tableId.value();
//        } else {
//            // 开启字段下划线申明
//            if (underCamel) {
//                column = StringUtils.camelToUnderline(column);
//            }
//            // 全局大写命名
//            if (dbConfig.isCapitalMode()) {
//                column = column.toUpperCase();
//            }
//        }
//        tableInfo.setKeyRelated(checkRelated(underCamel, property, column))
//                .setKeyColumn(column)
//                .setKeyProperty(property)
//                .setKeyType(reflector.getGetterType(property));
//    }
//
//    /**
//     * <p>
//     * 主键属性初始化
//     * </p>
//     *
//     * @param tableInfo 表信息
//     * @param field     字段
//     * @param reflector Reflector
//     * @return true 继续下一个属性判断，返回 continue;
//     */
//    private static boolean initTableIdWithoutAnnotation(GlobalConfig.DbConfig dbConfig, TableInfo tableInfo,
//                                                        Field field, Reflector reflector) {
//        final String property = field.getName();
//        if (DEFAULT_ID_NAME.equalsIgnoreCase(property)) {
//            if (field.getAnnotation(TableField.class) != null) {
//                logger.warn(String.format("This \"%s\" is the table primary key by default name for `id` in Class: \"%s\",So @TableField will not work!",
//                        property, tableInfo.getEntityType().getName()));
//            }
//            String column = property;
//            if (dbConfig.isCapitalMode()) {
//                column = column.toUpperCase();
//            }
//            tableInfo.setKeyRelated(checkRelated(tableInfo.isUnderCamel(), property, column))
//                    .setIdType(dbConfig.getIdType())
//                    .setKeyColumn(column)
//                    .setKeyProperty(property)
//                    .setKeyType(reflector.getGetterType(property));
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判定 related 的值
//     * <p>
//     * 为 true 表示不符合规则
//     *
//     * @param underCamel 驼峰命名
//     * @param property   属性名
//     * @param column     字段名
//     * @return related
//     */
//    public static boolean checkRelated(boolean underCamel, String property, String column) {
//        column = StringUtils.getTargetColumn(column);
//        String propertyUpper = property.toUpperCase(Locale.ENGLISH);
//        String columnUpper = column.toUpperCase(Locale.ENGLISH);
//        if (underCamel) {
//            // 开启了驼峰并且 column 包含下划线
//            return !(propertyUpper.equals(columnUpper) ||
//                    propertyUpper.equals(columnUpper.replace(StringPool.UNDERSCORE, StringPool.EMPTY)));
//        } else {
//            // 未开启驼峰,直接判断 property 是否与 column 相同(全大写)
//            return !propertyUpper.equals(columnUpper);
//        }
//    }
//
//    /**
//     * <p>
//     * 获取该类的所有属性列表
//     * </p>
//     *
//     * @param clazz 反射类
//     * @return 属性集合
//     */
//    public static List<Field> getAllFields(Class<?> clazz) {
//        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(clazz));
//        return fieldList.stream()
//                .filter(field -> {
//                    /* 过滤注解非表字段属性 */
//                    TableField tableField = field.getAnnotation(TableField.class);
//                    return (tableField == null || tableField.exist());
//                }).collect(toList());
//    }
//
//    public static KeyGenerator genKeyGenerator(String baseStatementId, TableInfo tableInfo, MapperBuilderAssistant builderAssistant) {
//        IKeyGenerator keyGenerator = GlobalConfigUtils.getKeyGenerator(builderAssistant.getConfiguration());
//        if (null == keyGenerator) {
//            throw new IllegalArgumentException("not configure IKeyGenerator implementation class.");
//        }
//        Configuration configuration = builderAssistant.getConfiguration();
//        //TODO 这里不加上builderAssistant.getCurrentNamespace()的会导致com.baomidou.mybatisplus.core.parser.SqlParserHelper.getSqlParserInfo越(chu)界(gui)
//        String id = builderAssistant.getCurrentNamespace() + StringPool.DOT + baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
//        ResultMap resultMap = new ResultMap.Builder(builderAssistant.getConfiguration(), id, tableInfo.getKeyType(), new ArrayList<>()).build();
//        MappedStatement mappedStatement = new MappedStatement.Builder(builderAssistant.getConfiguration(), id,
//                new StaticSqlSource(configuration, keyGenerator.executeSql(tableInfo.getKeySequence().value())), SqlCommandType.SELECT)
//                .keyProperty(tableInfo.getKeyProperty())
//                .resultMaps(Collections.singletonList(resultMap))
//                .build();
//        configuration.addMappedStatement(mappedStatement);
//        return new SelectKeyGenerator(mappedStatement, true);
//    }
//
//}
