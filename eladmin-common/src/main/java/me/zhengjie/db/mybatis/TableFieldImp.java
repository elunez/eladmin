/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package me.zhengjie.db.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.Annotation;

/**
 * JPA 转换使用 javax.persistence.Column
 *
 * @author liaojinlong
 * @since 2020/6/28 14:11
 */
public class TableFieldImp implements TableField {
    private String value = "";
    private Class<? extends Annotation> annotationType;
    private boolean exist = true;
    private String condition = "";
    private FieldStrategy insertStrategy = FieldStrategy.DEFAULT;
    private String update = "";
    private FieldStrategy updateStrategy = FieldStrategy.DEFAULT;
    private FieldStrategy whereStrategy = FieldStrategy.DEFAULT;
    private FieldFill fill = FieldFill.DEFAULT;
    private boolean select = true;
    private boolean keepGlobalFormat = false;
    private JdbcType jdbcType = JdbcType.UNDEFINED;
    private Class<? extends TypeHandler> typeHandler = UnknownTypeHandler.class;
    private String numericScale = "";

    /**
     * 数据库字段值,
     * 不需要配置该值的情况:
     * <li> 当 {@link com.baomidou.mybatisplus.core.MybatisConfiguration#mapUnderscoreToCamelCase} 为 true 时,
     * (mp下默认是true,mybatis默认是false), 数据库字段值.replace("_","").toUpperCase() == 实体属性名.toUpperCase() </li>
     * <li> 当 {@link com.baomidou.mybatisplus.core.MybatisConfiguration#mapUnderscoreToCamelCase} 为 false 时,
     * 数据库字段值.toUpperCase() == 实体属性名.toUpperCase()</li>
     */
    @Override
    public String value() {
        return value;
    }

    /**
     * 是否为数据库表字段
     * 默认 true 存在，false 不存在
     */
    @Override
    public boolean exist() {
        return exist;
    }

    /**
     * 字段 where 实体查询比较条件
     * 默认 `=` 等值
     */
    @Override
    public String condition() {
        return condition;
    }

    /**
     * 字段 update set 部分注入, 该注解优于 el 注解使用
     * <p>
     * 例1：@TableField(.. , update="%s+1") 其中 %s 会填充为字段
     * 输出 SQL 为：update 表 set 字段=字段+1 where ...
     * <p>
     * 例2：@TableField(.. , update="now()") 使用数据库时间
     * 输出 SQL 为：update 表 set 字段=now() where ...
     */
    @Override
    public String update() {
        return update;
    }

    /**
     * 字段验证策略之 insert: 当insert操作时，该字段拼接insert语句时的策略
     * IGNORED: 直接拼接 insert into table_a(column) values (#{columnProperty});
     * NOT_NULL: insert into table_a(<if test="columnProperty != null">column</if>) values (<if test="columnProperty != null">#{columnProperty}</if>)
     * NOT_EMPTY: insert into table_a(<if test="columnProperty != null and columnProperty!=''">column</if>) values (<if test="columnProperty != null and columnProperty!=''">#{columnProperty}</if>)
     *
     * @since 3.1.2
     */
    @Override
    public FieldStrategy insertStrategy() {
        return insertStrategy;
    }

    /**
     * 字段验证策略之 update: 当更新操作时，该字段拼接set语句时的策略
     * IGNORED: 直接拼接 update table_a set column=#{columnProperty}, 属性为null/空string都会被set进去
     * NOT_NULL: update table_a set <if test="columnProperty != null">column=#{columnProperty}</if>
     * NOT_EMPTY: update table_a set <if test="columnProperty != null and columnProperty!=''">column=#{columnProperty}</if>
     *
     * @since 3.1.2
     */
    @Override
    public FieldStrategy updateStrategy() {
        return updateStrategy;
    }

    /**
     * 字段验证策略之 where: 表示该字段在拼接where条件时的策略
     * IGNORED: 直接拼接 column=#{columnProperty}
     * NOT_NULL: <if test="columnProperty != null">column=#{columnProperty}</if>
     * NOT_EMPTY: <if test="columnProperty != null and columnProperty!=''">column=#{columnProperty}</if>
     *
     * @since 3.1.2
     */
    @Override
    public FieldStrategy whereStrategy() {
        return whereStrategy;
    }

    /**
     * 字段自动填充策略
     */
    @Override
    public FieldFill fill() {
        return fill;
    }

    /**
     * 是否进行 select 查询
     * <p>大字段可设置为 false 不加入 select 查询范围</p>
     */
    @Override
    public boolean select() {
        return select;
    }

    /**
     * 是否保持使用全局的 Format 的值
     * <p> 只生效于 既设置了全局的 Format 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 Format 不生效 </li>
     *
     * @since 3.1.1
     */
    @Override
    public boolean keepGlobalFormat() {
        return keepGlobalFormat;
    }

    /**
     * JDBC类型 (该默认值不代表会按照该值生效),
     * 只生效与 mp 自动注入的 method,
     * 建议配合 {@link TableName#autoResultMap()} 一起使用
     * <p>
     * {@link ResultMapping#jdbcType} and {@link ParameterMapping#jdbcType}
     *
     * @since 3.1.2
     */
    @Override
    public JdbcType jdbcType() {
        return jdbcType;
    }

    /**
     * 类型处理器 (该默认值不代表会按照该值生效),
     * 只生效与 mp 自动注入的 method,
     * 建议配合 {@link TableName#autoResultMap()} 一起使用
     * <p>
     * {@link ResultMapping#typeHandler} and {@link ParameterMapping#typeHandler}
     *
     * @since 3.1.2
     */
    @Override
    public Class<? extends TypeHandler> typeHandler() {
        return typeHandler;
    }

    /**
     * 指定小数点后保留的位数,
     * 只生效与 mp 自动注入的 method,
     * 建议配合 {@link TableName#autoResultMap()} 一起使用
     * <p>
     * {@link ParameterMapping#numericScale}
     *
     * @since 3.1.2
     */
    @Override
    public String numericScale() {
        return numericScale;
    }

    /**
     * Returns the annotation type of this annotation.
     *
     * @return the annotation type of this annotation
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return annotationType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setInsertStrategy(FieldStrategy insertStrategy) {
        this.insertStrategy = insertStrategy;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setUpdateStrategy(FieldStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    public void setWhereStrategy(FieldStrategy whereStrategy) {
        this.whereStrategy = whereStrategy;
    }

    public void setFill(FieldFill fill) {
        this.fill = fill;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setKeepGlobalFormat(boolean keepGlobalFormat) {
        this.keepGlobalFormat = keepGlobalFormat;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public void setTypeHandler(Class<? extends TypeHandler> typeHandler) {
        this.typeHandler = typeHandler;
    }

    public void setNumericScale(String numericScale) {
        this.numericScale = numericScale;
    }
}
