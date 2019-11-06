package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.util.Set;

/**
*
*
*/
@Data
public class FileSortQueryCriteria {

    @Query(type = Query.Type.IN, propName="id")
    private Set<String> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private String pid;
}