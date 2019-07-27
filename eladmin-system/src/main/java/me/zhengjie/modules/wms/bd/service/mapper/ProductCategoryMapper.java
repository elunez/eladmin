package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.MaterialCategory;
import me.zhengjie.modules.wms.bd.domain.ProductCategory;
import me.zhengjie.modules.wms.bd.service.dto.MaterialCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.ProductCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {
}
