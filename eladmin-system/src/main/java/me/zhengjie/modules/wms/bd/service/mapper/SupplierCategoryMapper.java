package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.SupplierCategory;
import me.zhengjie.modules.wms.bd.service.dto.SupplierCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierCategoryMapper extends EntityMapper<SupplierCategoryDTO, SupplierCategory> {
}
