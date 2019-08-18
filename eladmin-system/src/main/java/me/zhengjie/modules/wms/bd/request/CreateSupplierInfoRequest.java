package me.zhengjie.modules.wms.bd.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.bd.domain.SupplierInfo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-18
 */
@Data
public class CreateSupplierInfoRequest implements Serializable {


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
    private List<SupplierAddress> supplierAddress;

    // 供应商联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<SupplierContact> supplierContact;

    private Boolean status;

    // 供应商类别主键
    private Long supplierCategoryId;

    // 供应商类别名称
    private String supplierCategoryName;

    public void copy(SupplierInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
