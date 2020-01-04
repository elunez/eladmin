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

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 产品系列主键
    private Long productSeriesId;

    // 产品系列名称
    private String productSeriesName;
}