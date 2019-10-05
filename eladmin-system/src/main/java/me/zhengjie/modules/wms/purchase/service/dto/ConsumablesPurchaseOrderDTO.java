package me.zhengjie.modules.wms.purchase.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-06
*/
@Data
public class ConsumablesPurchaseOrderDTO implements Serializable {

    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    // 采购人主键
    private Long purchaseUserId;

    //  采购人姓名 
    private String purchaseUserName;

    // 状态
    private Integer status;

    // 审核状态
    private Integer auditStatus;

    private Long auditUserId;

    // 审核人姓名
    private String auditUserName;

    // 耗材采购单单据编号
    private String consumablesPurchaseOrderCode;
}