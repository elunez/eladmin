package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.bd.request.SupplierAddress;
import me.zhengjie.modules.wms.bd.request.SupplierContact;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class SupplierInfoDetailDTO extends SupplierInfoDTO implements Serializable {



    // 供应商地址地址数组[{“province”:””,”city”:””,”area”:””,”address_detail”:””,”sort”:””}]
    private List<SupplierAddress> supplierAddress;

    // 供应商联系人[{“sort”:””,”name”:””,”mobile”:””,”phone”:””,”email”:””,”qq”:””,”weixin”:””,”firstTag”:””}]firstTag 0:非首要联系人 1:首要联系人
    private List<SupplierContact> supplierContact;

}