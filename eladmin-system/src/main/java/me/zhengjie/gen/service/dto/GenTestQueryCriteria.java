package me.zhengjie.gen.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-11-19
*/
@Data
public class GenTestQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    // 精确
    @Query
    private Boolean status;

    // 时间段查询
    @Query(type = Query.Type.GREATER_THAN, propName = "date")
    private Timestamp dateStart;

    @Query(type = Query.Type.LESS_THAN, propName = "date")
    private Timestamp dateEnd;

    // 时间段查询
    @Query(type = Query.Type.GREATER_THAN, propName = "createTime")
    private Timestamp createTimeStart;

    @Query(type = Query.Type.LESS_THAN, propName = "createTime")
    private Timestamp createTimeEnd;
}