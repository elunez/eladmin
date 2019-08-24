package me.zhengjie.modules.wms.bd.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author jie
* @date 2019-08-03
*/
@Data
public class UpdateCustomerInfoRequest implements Serializable {

    private Long id;

    // 客户名称
    private String customerName;

    // 期初预收款
    private Long initialPreMoney;

    // 客户编号
    private String customerCode;

    // 备注
    private String remark;

    // 客户地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private List<CustomerAddress> customerAddress;

    // 客户联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<CustomerContact> customerContact;

    private Boolean status;

    public void copy(UpdateCustomerInfoRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}