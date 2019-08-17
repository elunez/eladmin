package me.zhengjie.modules.wms.order.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-03
*/
@Entity
@Data
@Table(name="s_customer_order_product")
public class CustomerOrderProduct implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    // 所属客户订单
    @Column(name = "customer_order_id")
    private Long customerOrderId;

    // 产品主键
    @Column(name = "product_id")
    private Long productId;

    // 产品名称
    @Column(name = "product_name")
    private Long productName;

    // 规格
    @Column(name = "specifications")
    private String specifications;

    // 产品单价
    @Column(name = "unit_price")
    private Long unitPrice;

    // 产品总价
    @Column(name = "total_price")
    private Long totalPrice;

    // 产品数量
    @Column(name = "product_number")
    private Long productNumber;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 状态
    private Boolean status;

    // 产品编号
    @Column(name = "product_code")
    private String productCode;

    public void copy(CustomerOrderProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}