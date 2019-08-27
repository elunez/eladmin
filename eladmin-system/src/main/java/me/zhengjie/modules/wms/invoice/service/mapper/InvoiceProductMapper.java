package me.zhengjie.modules.wms.invoice.service.mapper;

import me.zhengjie.mapper.EntityMapper;
import me.zhengjie.modules.wms.invoice.domain.InvoiceProduct;
import me.zhengjie.modules.wms.invoice.service.dto.InvoiceProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jie
* @date 2019-08-27
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceProductMapper extends EntityMapper<InvoiceProductDTO, InvoiceProduct> {

}