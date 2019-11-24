package me.zhengjie.modules.monitor.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Data
public class ServerQueryCriteria{

    // 模糊
    @Query(blurry = "name,address")
    private String blurry;
}