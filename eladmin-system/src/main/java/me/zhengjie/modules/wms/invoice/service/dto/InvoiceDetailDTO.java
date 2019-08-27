package me.zhengjie.modules.wms.invoice.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-27
 */
@Data
public class InvoiceDetailDTO extends InvoiceDTO implements Serializable {

    private List<InvoiceProductDTO> invoiceProductDTOList;

}
