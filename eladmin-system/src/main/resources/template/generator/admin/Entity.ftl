package ${package}.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
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
import java.io.Serializable;

/**
* @author ${author}
* @date ${date}
*/
@Entity
@Data
@Table(name="${tableName}")
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.remark != ''>
    /** ${column.remark} */
    </#if>
    <#if column.columnKey = 'PRI'>
    @Id
    <#if auto>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    </#if>
    @Column(name = "${column.columnName}"<#if column.columnKey = 'UNI'>,unique = true</#if><#if column.istNotNull && column.columnKey != 'PRI'>,nullable = false</#if>)
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    <#if column.dateAnnotation??>
    <#if column.dateAnnotation = 'CreationTimestamp'>
    @CreationTimestamp
    <#else>
    @UpdateTimestamp
    </#if>
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}