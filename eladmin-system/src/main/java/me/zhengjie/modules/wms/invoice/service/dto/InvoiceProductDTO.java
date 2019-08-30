package me.zhengjie.modules.wms.invoice.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-27
*/
@Data
public class InvoiceProductDTO implements Serializable {

    private Long id;

    // 创建时间
    private Timestamp createStatus;

    // 更新时间
    private Timestamp updateStatus;

    // 状态
    private Boolean status;

    // 产品主键
    private Long productId;

    // 产品名称
    private String productName;

    // 产品编号
    private String productCode;

    // 规格
    private String specifications;

    // 订单数量
    private Long customerOrderNumber;

    // 实际发货单数量
    private Long actualInvoiceNumber;

    // 瘦瘦金额
    private Long salePrice;

    // 备注
    private String remark;
}