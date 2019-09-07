package me.zhengjie.modules.wms.invoice.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String createTimeStr;

    private Timestamp updateTime;

    private String updateTimeStr;

    // 客户订单编号
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

    // 物流单号
    private String logisticsCode;

    // 销售发货单号
    private String saleInvoiceCode;

    // 状态
    private Boolean status;

    // 备注
    private String remark;

    private Long customerId;

    private String customerName;
}