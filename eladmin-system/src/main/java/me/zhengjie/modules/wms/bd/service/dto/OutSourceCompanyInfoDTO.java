package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class OutSourceCompanyInfoDTO implements Serializable {

    private Long id;

    // 委外公司名称
    private String outSourceCompanyName;

    // 期初预收款
    private Long initialPreMoney;

    // 委外公司编号
    private String outSourceCompanyCode;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 备注
    private String remark;

    // 首要联系人姓名
    private String firstContactName;

    // 首要联系人手机
    private String firstContactMobile;

    // 首要联系人地址
    private String firstContactAddress;

    private Boolean status;
}