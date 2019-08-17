package me.zhengjie.modules.wms.outSourceProductSheet.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceProcessSheetProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-17
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutSourceProcessSheetProductMapper extends EntityMapper<OutSourceProcessSheetProductDTO, OutSourceProcessSheetProduct> {

}