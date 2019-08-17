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
@Table(name="s_customer_order")
public class CustomerOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 所属客户主键
    @Column(name = "customer_id")
    private Long customerId;

    // 客户名称
    @Column(name = "customer_name")
    private String customerName;

    // 订单日期
    @Column(name = "order_date")
    private Timestamp orderDate;

    // 交货日期
    @Column(name = "delivery_date")
    private Timestamp deliveryDate;

    // 客户订单编号
    @Column(name = "customer_order_code")
    private String customerOrderCode;

    // 制单人主键
    @Column(name = "customer_order_maker_id")
    private Long customerOrderMakerId;

    // 制单人姓名
    @Column(name = "customer_order_maker_name")
    private String customerOrderMakerName;

    // 交货方式code
    @Column(name = "delivery_way_code")
    private String deliveryWayCode;

    // 交货方式名称
    @Column(name = "delivery_way_name")
    private String deliveryWayName;

    // 付款方式
    @Column(name = "pay_way_code")
    private String payWayCode;

    // 付款方式名称
    @Column(name = "pay_way_name")
    private String payWayName;

    // 收货地址
    @Column(name = "delivery_address")
    private String deliveryAddress;

    // 收货人
    @Column(name = "delivery_user")
    private String deliveryUser;

    // 收货人联系方式
    @Column(name = "delivery_user_contact")
    private String deliveryUserContact;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 总额
    @Column(name = "total_money")
    private Long totalMoney;

    // 总数量
    @Column(name = "total_number")
    private Long totalNumber;

    // 状态
    private Boolean status;

    public void copy(CustomerOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}