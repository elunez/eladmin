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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.annotation.Annotation;

/**
 * JPA 转换 ID
 * javax.persistence.Id
 * javax.persistence.Column
 *
 * @author liaojinlong
 * @since 2020/6/28 14:01
 */
public class TableIdImp implements TableId {
    private String value = "";
    private IdType type = IdType.NONE;
    private Class<? extends Annotation> annotationType;


    /**
     * 字段值（驼峰命名方式，该值可无）
     */
    @Override
    public String value() {
        return value;
    }

    /**
     * 主键ID
     * {@link IdType}
     */
    @Override
    public IdType type() {
        return type;
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

    public void setType(IdType type) {
        this.type = type;
    }

    public void setAnnotationType(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }
}
