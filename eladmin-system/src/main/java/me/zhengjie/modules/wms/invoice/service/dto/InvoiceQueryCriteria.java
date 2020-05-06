package me.zhengjie.modules.wms.invoice.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-08-27
*/
@Data
public class InvoiceQueryCriteria {

    // 客户订单编号
    private String customerOrderCode;

    // 客户名称
    private String customerName;
}