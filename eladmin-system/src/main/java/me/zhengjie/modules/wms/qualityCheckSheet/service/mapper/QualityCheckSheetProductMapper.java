package me.zhengjie.modules.wms.qualityCheckSheet.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheetProduct;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QualityCheckSheetProductMapper extends EntityMapper<QualityCheckSheetProductDTO, QualityCheckSheetProduct> {

}