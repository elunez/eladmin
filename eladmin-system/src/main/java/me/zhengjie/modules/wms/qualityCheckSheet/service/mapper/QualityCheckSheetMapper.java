package me.zhengjie.modules.wms.qualityCheckSheet.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.qualityCheckSheet.domain.QualityCheckSheet;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QualityCheckSheetMapper extends EntityMapper<QualityCheckSheetDTO, QualityCheckSheet> {

}