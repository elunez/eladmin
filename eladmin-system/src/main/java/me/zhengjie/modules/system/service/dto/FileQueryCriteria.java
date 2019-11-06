package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.util.Set;

/**
*
*
*/
@Data
public class FileQueryCriteria {

    @Query(propName = "id", type = Query.Type.IN, joinName = "fileSort")
    private Set<String> fileSortIds;

    @Query(type = Query.Type.INNER_LIKE)
    private String fileName;
    @Query(type = Query.Type.INNER_LIKE)
    private String uploader;

    private String fileSortId;
//
//    @Query
//    private Boolean enabled;
//
//    @Query
//    private String pid;
}