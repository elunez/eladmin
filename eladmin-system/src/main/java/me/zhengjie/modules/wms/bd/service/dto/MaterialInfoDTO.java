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
    private Long id;

    // 所属物料分类主键
    private Long materialCategoryId;

    // 物料分类名称
    private String materialCategoryName;

    // 物料名称
    private String name;

    // 物料编码
    private String materialCode;

    // 物料规格
    private String specifications;

    // 所属计量单位主键
    private Long measureUnitId;

    // 所属计量单位名称
    private String measureUnitName;

    // 物料期初合计期初总价格
    private Long materialInitialSetupTotalPrice;

    // 物料期初合计总数量
    private String materialInitialSetupTotalNumber;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;
}