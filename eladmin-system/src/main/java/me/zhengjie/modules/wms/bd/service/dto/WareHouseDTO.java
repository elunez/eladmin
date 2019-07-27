package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Data
public class WareHouseDTO {

    private Long id;

    private String name;

    private String wareHouseCode;

    private Boolean status;

    private Timestamp createTime;

    private Timestamp updateTime;

}
