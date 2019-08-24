package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-08-21
 */
@Data
public class ProductInventoryWarning implements Serializable {
    private String wareHouseCode;

    private String wareHouseName;

    private String minimumInventory;

    private String highestInventory;

}
