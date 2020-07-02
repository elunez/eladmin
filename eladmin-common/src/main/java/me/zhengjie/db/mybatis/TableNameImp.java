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

import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.annotation.Annotation;

/**
 * JAP 转换使用
 *
 * @author liaojinlong
 * javax.persistence.Table
 * @since 2020/6/28 13:56
 */
public class TableNameImp implements TableName {
    private String value = "";
    private String schema = "";
    private String resultMap = "";
    private boolean keepGlobalPrefix = false;
    private String[] excludeProperty = new String[0];
    private boolean autoResultMap = false;
    private Class<? extends Annotation> annotationType;

    /**
     * 实体对应的表名
     */
    @Override
    public String value() {
        return value;
    }

    /**
     * schema
     *
     * @since 3.1.1
     */
    @Override
    public String schema() {
        return schema;
    }


    /**
     * 是否保持使用全局的 tablePrefix 的值
     * <p> 只生效于 既设置了全局的 tablePrefix 也设置了上面 {@link #value()} 的值 </p>
     * <li> 如果是 false , 全局的 tablePrefix 不生效 </li>
     *
     * @since 3.1.1
     */
    @Override
    public boolean keepGlobalPrefix() {
        return keepGlobalPrefix;
    }

    /**
     * 实体映射结果集,
     * 只生效与 mp 自动注入的 method
     */
    @Override
    public String resultMap() {
        return resultMap;
    }

    /**
     * 是否自动构建 resultMap 并使用,
     * 只生效与 mp 自动注入的 method,
     * 如果设置 resultMap 则不会进行 resultMap 的自动构建并注入,
     * 只适合个别字段 设置了 typeHandler 或 jdbcType 的情况
     *
     * @since 3.1.2
     */
    @Override
    public boolean autoResultMap() {
        return autoResultMap;
    }


    /**
     * 需要排除的属性名
     *
     * @since 3.3.1
     */
    @Override
    public String[] excludeProperty() {
        return excludeProperty;
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

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setResultMap(String resultMap) {
        this.resultMap = resultMap;
    }

    public void setKeepGlobalPrefix(boolean keepGlobalPrefix) {
        this.keepGlobalPrefix = keepGlobalPrefix;
    }

    public void setExcludeProperty(String[] excludeProperty) {
        this.excludeProperty = excludeProperty;
    }

    public void setAutoResultMap(boolean autoResultMap) {
        this.autoResultMap = autoResultMap;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }
}
