package me.zhengjie.modules.wms.outSourceProductSheet.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.outSourceProductSheet.domain.OutSourceInspectionCertificateProduct;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceInspectionCertificateProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-10-01
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OutSourceInspectionCertificateProductMapper extends EntityMapper<OutSourceInspectionCertificateProductDTO, OutSourceInspectionCertificateProduct> {

}