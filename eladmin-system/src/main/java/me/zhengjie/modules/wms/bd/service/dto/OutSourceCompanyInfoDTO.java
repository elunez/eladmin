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

    // 委外公司地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private String outSourceCompanyAddress;

    // 委外公司联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private String outSourceCompanyContact;

    private Boolean status;
}