/*
*  Copyright 2019-2025 Zheng Jie
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
package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
<#if isNotNullColumns??>
import javax.validation.constraints.*;
</#if>
<#if hasDateAnnotation>
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
</#if>
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#assign notBlankUsed = false>
<#assign notNullUsed = false>
<#if columns??>
    <#list columns as column>
        <#if column.istNotNull && column.columnKey != 'PRI'>
            <#if column.columnType = 'String'>
                <#assign notBlankUsed = true>
            <#else>
                <#assign notNullUsed = true>
            </#if>
        </#if>
    </#list>
</#if>
<#if notBlankUsed>
import javax.validation.constraints.NotBlank;
</#if>
<#if notNullUsed>
import javax.validation.constraints.NotNull;
</#if>
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author ${author}
* @date ${date}
**/
@Entity
@Data
@Table(name="${tableName}")
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.columnKey = 'PRI'>
    @Id
    <#if auto>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    </#if>
    @Column(name = "`${column.columnName}`"<#if column.columnKey = 'UNI'>,unique = true</#if><#if column.istNotNull && column.columnKey != 'PRI'>,nullable = false</#if>)
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    <#if (column.dateAnnotation)?? && column.dateAnnotation != ''>
    <#if column.dateAnnotation = 'CreationTimestamp'>
    @CreationTimestamp
    <#else>
    @UpdateTimestamp
    </#if>
    </#if>
    <#if column.remark != ''>
    @ApiModelProperty(value = "${column.remark}")
    <#else>
    @ApiModelProperty(value = "${column.changeColumnName}")
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
