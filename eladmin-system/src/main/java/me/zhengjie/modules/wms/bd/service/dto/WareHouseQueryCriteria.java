package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Data
public class WareHouseQueryCriteria implements Serializable {

    @Query
    private Long id;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean status;
}
