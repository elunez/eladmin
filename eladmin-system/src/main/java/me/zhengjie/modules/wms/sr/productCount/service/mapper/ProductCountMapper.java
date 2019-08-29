package me.zhengjie.modules.wms.sr.productCount.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.sr.productCount.domain.ProductCount;
import me.zhengjie.modules.wms.sr.productCount.service.dto.ProductCountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-29
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCountMapper extends EntityMapper<ProductCountDTO, ProductCount> {

}