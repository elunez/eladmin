package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-08-22
 * //[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”ware_house_type_code”:””,“ ware_house_type_name”:””,
 * 	//“material_code”:””,“material_name”:””,“specifications”:””,“unit_price”:””,“total_price”:””,
 * 	//”minimum_inventory”:””,”highest_inventory”:””}]
 */
@Data
public class MaterialInitialSetup implements Serializable {

    private Integer sort;

    private String wareHouseCode;

    private String wareHouseName;

    private String wareHouseTypeCode;

    private String wareHouseTypeName;

    private String materialCode;

    private String materialName;

    private String specifications;

    private Long unitPrice;

    private Long totalPrice;

    private String minimumInventory;

    private String highestInventory;

}
