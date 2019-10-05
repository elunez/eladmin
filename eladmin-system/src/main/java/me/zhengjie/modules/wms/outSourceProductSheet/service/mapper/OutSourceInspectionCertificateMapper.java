package me.zhengjie.modules.wms.outSourceProductSheet.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificate;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-10-01
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutSourceInspectionCertificateMapper extends EntityMapper<OutSourceInspectionCertificateDTO, OutSourceInspectionCertificate> {

}