package ${package}.service.dto;

import lombok.Data;
<#if queryHasTimestamp>
import java.sql.Timestamp;
</#if>
<#if queryHasBigDecimal>
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

<#if column.queryType = '='>
    // 精确
    @Query
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = 'Like'>
    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '!='>
    // 不等于
    @Query(type = Query.Type.NOT_EQUAL)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '>='>
    // 大于等于
    @Query(type = Query.Type.GREATER_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '<='>
    // 小于等于
    @Query(type = Query.Type.LESS_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
    </#list>
</#if>
<#if dateRanges??>
    <#list dateRanges as column>

    // 时间段查询
    @Query(type = Query.Type.GREATER_THAN, propName = "${column.changeColumnName}")
    private ${column.columnType} ${column.changeColumnName}Start;

    @Query(type = Query.Type.LESS_THAN, propName = "${column.changeColumnName}")
    private ${column.columnType} ${column.changeColumnName}End;
    </#list>
</#if>
}