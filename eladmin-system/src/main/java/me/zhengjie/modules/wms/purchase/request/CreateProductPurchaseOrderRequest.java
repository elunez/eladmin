package me.zhengjie.modules.wms.purchase.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 新增产品产品采购单请求参数
 * @author 黄星星
 * @date 2019-10-06
 */
@Data
public class CreateProductPurchaseOrderRequest implements Serializable {

    // 采购人主键
    private Long purchaseUserId;

    //  采购人姓名
    private String purchaseUserName;

    // 产品采购单单据编号
    private String productPurchaseOrderCode;

    // 产品采购单商品信息
    private List<ProductPurchaseOrderProductRequest> productPurchaseOrderProductList;

    public void copy(CreateProductPurchaseOrderRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
