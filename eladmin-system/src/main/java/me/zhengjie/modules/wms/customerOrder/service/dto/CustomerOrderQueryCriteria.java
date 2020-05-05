package me.zhengjie.modules.wms.customerOrder.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-08-03
*/
@Data
public class CustomerOrderQueryCriteria {

    // 客户订单编号
    private String customerOrderCode;

    // 客户名称
    private String customerName;
}