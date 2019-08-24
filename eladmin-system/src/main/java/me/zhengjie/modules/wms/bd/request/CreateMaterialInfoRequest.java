package me.zhengjie.modules.wms.bd.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.bd.domain.MaterialInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-24
 */
@Data
public class CreateMaterialInfoRequest implements Serializable {
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

    // 产品库存预警
    private List<MaterialInventoryWarning> materialInventoryWarningList;

    // 产品期初设置
    private List<MaterialInitialSetup> materialInitialSetupList;

    // 物料期初合计期初总价格
    private Integer materialInitialSetupTotalPrice;

    // 物料期初合计总数量
    private String materialInitialSetupTotalNumber;

    private Boolean status;

    public void copy(MaterialInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
