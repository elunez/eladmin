package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.ProductSeries;
import me.zhengjie.modules.wms.bd.service.dto.ProductSeriesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huangxingxing
* @date 2020-01-04
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductSeriesMapper extends EntityMapper<ProductSeriesDTO, ProductSeries> {

}