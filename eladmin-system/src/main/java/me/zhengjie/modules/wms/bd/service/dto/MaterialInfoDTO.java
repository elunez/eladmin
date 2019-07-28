package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author 黄星星
* @date 2019-07-27
*/
@Data
public class MaterialInfoDTO implements Serializable {

    // 主键
    private Integer id;

    // 所属物料分类主键
    private Integer materialCategoryId;

    // 物料分类名称
    private String materialCategoryName;

    // 物料名称
    private String name;

    // 物料规格
    private String specifications;

    // 所属计量单位主键
    private Integer measureUnitId;

    // 所属计量单位名称
    private String measureUnitName;

    // 产品库存预警[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,”minimum_inventory”:””,”highest_inventory”:””}]
    private String materialInventoryWarning;

    // 产品期初设置[{“sort”:””,”ware_house_code”:””,”ware_house_name”:””,“initial_setup_numer”:””,“initial_setup_total_price”:””}]
    private String materialInitialSetup;

    // 物料期初合计期初总价格

    private Integer materialInitialSetupTotalPrice;

    // 物料期初合计总数量
    private String materialInitialSetupTotalNumber;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;
}