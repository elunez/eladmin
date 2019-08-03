package me.zhengjie.modules.wms.bd.domain;

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
@Table(name="bd_supplier_info")
public class SupplierInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 供应商名称
    @Column(name = "supplier_name")
    private String supplierName;

    // 期初预收款
    @Column(name = "initial_pre_money")
    private Long initialPreMoney;

    // 供应商编号
    @Column(name = "supplier_code")
    private String supplierCode;

    // 创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 供应商地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    @Column(name = "supplier_address")
    private String supplierAddress;

    // 供应商联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    @Column(name = "supplier_contact")
    private String supplierContact;

    @Column(name = "status")
    private Boolean status;

    // 供应商类别主键
    @Column(name = "supplier_category_id")
    private Long supplierCategoryId;

    // 供应商类别名称
    @Column(name = "supplier_category_name")
    private String supplierCategoryName;

    public void copy(SupplierInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}