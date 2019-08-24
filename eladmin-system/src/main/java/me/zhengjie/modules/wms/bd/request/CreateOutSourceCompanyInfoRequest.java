package me.zhengjie.modules.wms.bd.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.zhengjie.modules.wms.bd.domain.OutSourceCompanyInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-24
 */
@Data
public class CreateOutSourceCompanyInfoRequest implements Serializable {


    // 委外公司名称
    private String outSourceCompanyName;

    // 期初预收款
    private Long initialPreMoney;

    // 委外公司编号
    private String outSourceCompanyCode;

    // 备注
    private String remark;

    // 委外公司地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private List<OutSourceCompanyAddress> outSourceCompanyAddress;

    // 委外公司联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<OutSourceCompanyContact> outSourceCompanyContact;

    //    @Column(name = "status")
    private Boolean status;

    public void copy(OutSourceCompanyInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
