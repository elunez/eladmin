package me.zhengjie.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageQueryCriteria{

    // 模糊
    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.GREATER_THAN,propName = "createTime")
    private Timestamp startTime;

    @Query(type = Query.Type.LESS_THAN,propName = "createTime")
    private Timestamp endTime;
}