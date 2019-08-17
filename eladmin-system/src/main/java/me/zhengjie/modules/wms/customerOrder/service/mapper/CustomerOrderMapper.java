package me.zhengjie.modules.wms.customerOrder.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.customerOrder.service.dto.CustomerOrderDTO;
import me.zhengjie.modules.wms.customerOrder.domain.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-03
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerOrderMapper extends EntityMapper<CustomerOrderDTO, CustomerOrder> {

}