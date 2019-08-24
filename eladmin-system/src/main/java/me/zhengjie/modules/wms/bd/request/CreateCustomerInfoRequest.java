package me.zhengjie.modules.wms.bd.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-24
 */
@Data
public class CreateCustomerInfoRequest implements Serializable {

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

    // 客户地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private List<CustomerAddress> supplierAddress;

    // 客户联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<CustomerContact> customerContact;

}
