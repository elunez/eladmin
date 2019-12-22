package me.zhengjie.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

import me.zhengjie.annotation.Query;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageQueryCriteria{

    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}