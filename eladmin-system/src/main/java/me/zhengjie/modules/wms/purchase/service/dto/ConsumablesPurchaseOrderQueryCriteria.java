package me.zhengjie.modules.wms.purchase.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-10-06
*/
@Data
public class ConsumablesPurchaseOrderQueryCriteria{

    // 耗材单据编号
    private String consumablesPurchaseOrderCode;
}