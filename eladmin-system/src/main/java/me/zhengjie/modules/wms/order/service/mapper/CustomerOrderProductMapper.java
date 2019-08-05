package me.zhengjie.modules.wms.order.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.order.domain.CustomerOrderProduct;
import me.zhengjie.modules.wms.order.service.dto.CustomerOrderProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-03
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerOrderProductMapper extends EntityMapper<CustomerOrderProductDTO, CustomerOrderProduct> {

}