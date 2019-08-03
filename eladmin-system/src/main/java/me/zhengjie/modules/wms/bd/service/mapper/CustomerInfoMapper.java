package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.CustomerInfo;
import me.zhengjie.modules.wms.bd.service.dto.CustomerInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-03
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerInfoMapper extends EntityMapper<CustomerInfoDTO, CustomerInfo> {

}