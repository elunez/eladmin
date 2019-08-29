package me.zhengjie.modules.wms.invoice.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-27
*/
@Entity
@Data
@Table(name="s_invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    // 客户订单编号
    @Column(name = "customer_order_code")
    private String customerOrderCode;

    // 收货地址
    @Column(name = "delivery_address")
    private String deliveryAddress;

    // 收货人
    @Column(name = "consignee")
    private String consignee;

    // 联系方式
    @Column(name = "contact_way")
    private String contactWay;

    // 发票号
    @Column(name = "invoice_number")
    private String invoiceNumber;

    // 物流公司
    @Column(name = "logistics_company")
    private String logisticsCompany;

    // 销售发货单号
    @Column(name = "sale_invoice_code")
    private String saleInvoiceCode;

    // 状态
    @Column(name = "status")
    private Boolean status;

    // 备注
    @Column(name = "remark")
    private String remark;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    public void copy(Invoice source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}