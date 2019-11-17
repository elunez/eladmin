package me.zhengjie.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.sql.Timestamp;

/**
 * @author Zheng Jie
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiniuQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;

    @Query(type = Query.Type.GREATER_THAN,propName = "updateTime")
    private Timestamp startTime;

    @Query(type = Query.Type.LESS_THAN,propName = "updateTime")
    private Timestamp endTime;
}
