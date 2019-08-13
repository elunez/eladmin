package me.zhengjie.modules.wms.bd.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-03
*/
@Entity
@Data
@Table(name="bd_customer_info")
public class CustomerInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 客户名称
    @Column(name = "customer_name",nullable = false)
    private String customerName;

    // 期初预收款
    @Column(name = "initial_pre_money")
    private Long initialPreMoney;

    // 客户编号
    @Column(name = "customer_code",nullable = false)
    private String customerCode;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 客户地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    @Column(name = "customer_address",nullable = false)
    private String customerAddress;

    // 客户联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    @Column(name = "customer_contact")
    private String customerContact;

//    @NotNull
    private Boolean status;

    public void copy(CustomerInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}