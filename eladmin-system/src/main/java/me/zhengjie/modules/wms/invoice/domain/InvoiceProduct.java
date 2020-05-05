package me.zhengjie.modules.wms.invoice.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-27
*/
@Entity
@Data
@Table(name="s_invoice_product")
public class InvoiceProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 创建时间
    @Column(name = "create_status")
    @CreationTimestamp
    private Timestamp createStatus;

    // 更新时间
    @Column(name = "update_status")
    @CreationTimestamp
    private Timestamp updateStatus;

    // 状态
    @Column(name = "status")
    private Boolean status;

    // 产品主键
    @Column(name = "product_id",nullable = false)
    private Long productId;

    // 产品名称
    @Column(name = "product_name",nullable = false)
    private String productName;

    // 产品编号
    @Column(name = "product_code",nullable = false)
    private String productCode;

    // 规格
    @Column(name = "specifications")
    private String specifications;

    // 订单数量
    @Column(name = "customer_order_number",nullable = false)
    private Long customerOrderNumber;

    // 实际发货单数量
    @Column(name = "actual_invoice_number",nullable = false)
    private Long actualInvoiceNumber;

    // 销售金额
    @Column(name = "sale_price")
    private Long salePrice;

    // 单价
    @Column(name = "unit_price")
    private Long unitPrice;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 销售发货单id
    @Column(name = "invoice_id")
    private Long invoiceId;

    // 客户订单编号
    @Column(name = "customer_order_code")
    private String customerOrderCode;

    public void copy(InvoiceProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}