package ${package}.service.dto;

import lombok.Data;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if queryColumns??>
import me.zhengjie.annotation.Query;
</#if>

/**
* @author ${author}
* @date ${date}
*/
@Data
public class ${className}QueryCriteria{
<#if queryColumns??>
    <#list queryColumns as column>

    <#if column.columnQuery = '1'>
    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    </#if>
    <#if column.columnQuery = '2'>
    // 精确
    @Query
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}