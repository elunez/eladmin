package me.zhengjie.modules.wms.purchase.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
* @author jie
* @date 2019-10-06
*/
@Data
public class CreateConsumablesPurchaseOrderRequest implements Serializable {

    // 采购人主键
    private Long purchaseUserId;

    //  采购人姓名 
    private String purchaseUserName;

    // 耗材采购单单据编号
    private String consumablesPurchaseOrderCode;

    private List<ConsumablesPurchaseOrderProductRequest> consumablesPurchaseOrderProductList;

    public void copy(CreateConsumablesPurchaseOrderRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}