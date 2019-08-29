package me.zhengjie.modules.wms.invoice.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-27
*/
@Data
public class InvoiceDTO implements Serializable {

    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String customerOrderCode;

    // 收货地址
    private String deliveryAddress;

    // 收货人
    private String consignee;

    // 联系方式
    private String contactWay;

    // 发票号
    private String invoiceNumber;

    // 物流公司
    private String logisticsCompany;

    // 销售发货单号
    private String saleInvoiceCode;

    // 状态
    private Boolean status;

    // 备注
    private String remark;
}