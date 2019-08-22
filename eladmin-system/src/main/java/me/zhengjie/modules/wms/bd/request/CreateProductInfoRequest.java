package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-21
 */

@Data
public class CreateProductInfoRequest implements Serializable {

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
    private Long unitPrice;

    // 产品库存预警
    private List<ProductInventoryWarning> productInventoryWarningList;

    // 产品期初设置
    private List<ProductInitialSetup> productInitialSetupList;

}
