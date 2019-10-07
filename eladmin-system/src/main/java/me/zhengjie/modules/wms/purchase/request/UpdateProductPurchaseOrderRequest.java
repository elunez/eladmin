package me.zhengjie.modules.wms.purchase.request;

import lombok.Data;
import me.zhengjie.modules.wms.purchase.service.dto.ConsumablesPurchaseOrderProductDTO;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderProductDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 更新产品采购单
 * @author 黄星星
 * @date 2019-10-07
 */
@Data
public class UpdateProductPurchaseOrderRequest implements Serializable {
    private Long id;

    private List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductList;
}
