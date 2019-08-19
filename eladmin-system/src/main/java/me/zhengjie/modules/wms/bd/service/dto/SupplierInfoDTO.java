package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.bd.request.SupplierAddress;
import me.zhengjie.modules.wms.bd.request.SupplierContact;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;


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

    private Boolean status;

    // 供应商类别主键
    private Long supplierCategoryId;

    // 供应商类别名称
    private String supplierCategoryName;

    // 首要联系人姓名
    private String firstContactName;

    // 首要联系人手机
    private String firstContactMobile;

    // 首要联系人地址
    private String firstContactAddress;

    // 应付款
    private Long upPayMoney;
}