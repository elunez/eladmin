package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Data
public class ProductCategoryDTO {

    private Long id;

    private String name;

    private Boolean status;

    private Timestamp createTime;

    private Timestamp updateTime;

}
