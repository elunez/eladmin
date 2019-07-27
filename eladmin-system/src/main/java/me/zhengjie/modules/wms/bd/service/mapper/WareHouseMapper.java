package me.zhengjie.modules.wms.bd.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.bd.domain.SpendCategory;
import me.zhengjie.modules.wms.bd.domain.WareHouse;
import me.zhengjie.modules.wms.bd.service.dto.SpendCategoryDTO;
import me.zhengjie.modules.wms.bd.service.dto.WareHouseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 黄星星
 * @date 2019-07-27
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WareHouseMapper extends EntityMapper<WareHouseDTO, WareHouse> {
}
