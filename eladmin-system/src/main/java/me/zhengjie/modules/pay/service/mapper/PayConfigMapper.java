package me.zhengjie.modules.pay.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.pay.domain.PayConfig;
import me.zhengjie.modules.pay.service.dto.PayConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author xpay
* @date 2019-06-02
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PayConfigMapper extends EntityMapper<PayConfigDTO, PayConfig> {

}