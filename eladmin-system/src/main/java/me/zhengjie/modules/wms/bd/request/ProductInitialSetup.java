package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-08-22
 */
@Data
public class ProductInitialSetup implements Serializable {

    private Integer sort;

    private String wareHouseCode;

    private String wareHouseName;

    private String wareHouseTypeCode;

    private String wareHouseTypeName;
}
