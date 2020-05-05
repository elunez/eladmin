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

    // 备注：为了前端方便，特意加上这个字段，因为客户订单里面的产品数量用的这个，销售发货单里面改了字段名字叫customerOrderNumber 所以 customerOrderNumber  = productNumber
    private Long productNumber;

    // 实际发货单数量
    private Long actualInvoiceNumber;

    // 瘦瘦金额
    private Long salePrice;

    private Long unitPrice;

    // 备注
    private String remark;
}