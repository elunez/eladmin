package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class SupplierInfoDTO implements Serializable {

    //主键
    private Long id;

    // 供应商名称
    private String supplierName;

    // 期初预收款
    private Long initialPreMoney;

    // 供应商编号
    private String supplierCode;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 备注
    private String remark;

    // 供应商地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private String supplierAddress;

    // 供应商联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private String supplierContact;

    private Boolean status;

    // 供应商类别主键
    private Long supplierCategoryId;

    // 供应商类别名称
    private String supplierCategoryName;
}