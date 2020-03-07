package me.zhengjie.gen.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author Zheng Jie
* @date 2020-03-07
*/
@Data
public class GenTestQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 不为空 */
    @Query(type = Query.Type.NOT_NULL)
    private Integer sex;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}