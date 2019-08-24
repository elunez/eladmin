package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class CustomerInfoDTO implements Serializable {

    private Long id;

    // 客户名称
    private String customerName;

    // 期初预收款
    private Long initialPreMoney;

    // 客户编号
    private String customerCode;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 备注
    private String remark;

    // 状态
    private Boolean status;

    // 首要联系人姓名
    private String firstContactName;

    // 首要联系人手机
    private String firstContactMobile;

    // 首要联系人地址
    private String firstContactAddress;

}