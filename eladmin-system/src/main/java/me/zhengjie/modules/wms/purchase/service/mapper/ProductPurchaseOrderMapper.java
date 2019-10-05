package me.zhengjie.modules.wms.purchase.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.purchase.domain.ProductPurchaseOrder;
import me.zhengjie.modules.wms.purchase.service.dto.ProductPurchaseOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-10-06
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductPurchaseOrderMapper extends EntityMapper<ProductPurchaseOrderDTO, ProductPurchaseOrder> {

}