package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

/**
 * 物料库存预警信息
 * [{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”minimum_inventory”:””,”highest_inventory”:””}]
 * @author 黄星星
 * @date 2019-07-28
 */
@Data
public class MaterialInventoryWarning {
    // 排序
    private Integer sort;

    // 仓库编码
    private String wareHouseCode;

    // 仓库名称
    private String wareHouseName;

    // 最低库存
    private Integer miniNumInventory;

    // 最高库存
    private Integer highestInventory;

}