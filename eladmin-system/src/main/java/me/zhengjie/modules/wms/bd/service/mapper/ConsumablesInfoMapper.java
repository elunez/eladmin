package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.ConsumablesInfo;
import me.zhengjie.modules.wms.bd.service.dto.ConsumablesInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-10-05
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsumablesInfoMapper extends EntityMapper<ConsumablesInfoDTO, ConsumablesInfo> {

}