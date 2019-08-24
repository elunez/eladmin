package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.bd.request.CustomerContact;
import me.zhengjie.modules.wms.bd.request.OutSourceCompanyAddress;
import me.zhengjie.modules.wms.bd.request.OutSourceCompanyContact;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-24
 */
@Data
public class OutSourceCompanyInfoDetailDTO extends  OutSourceCompanyInfoDTO implements Serializable {

    // 客户地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private List<OutSourceCompanyAddress> outSourceCompanyAddress;

    // 客户联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<OutSourceCompanyContact> outSourceCompanyContact;
}
