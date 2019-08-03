package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author 黄星星
* @date 2019-07-27
*/
@Data
public class ProductInfoDTO implements Serializable {

    // 主键
    private long id;

    // 所属产品分类
    private long productCategoryId;

    // 产品分类名称
    private String productCategoryName;

    // 产品编号
    private String productCode;

    // 产品名称
    private String name;

    // 产品规格
    private String specifications;

    // 所属计量单位主键
    private long measureUnitId;

    // 所属计量单位名称
    private String measureUnitName;

    // 产品单价(保留两位小数) 单位:元 
    private long unitPrice;

    // 产品库存预警[{“sort”:1,”ware_house_code”:””,”ware_house_name”:””,”minimum_inventory”:””,”highest_inventory”:””}]
    private String productInventoryWarning;

    // 产品期初设置
//    [{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”ware_house_type_code”:””,“ ware_house_type_name”:””,
//        “material_code”:””,“material_name”:””,“specifications”:””,“unit_price”:””,
//        “total_price”:””,”minimum_inventory”:””,”highest_inventory”:””
//    }]
    private String productInitialSetup;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;
}