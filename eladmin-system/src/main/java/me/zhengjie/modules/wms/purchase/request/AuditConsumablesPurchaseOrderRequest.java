package me.zhengjie.modules.wms.purchase.request;

import lombok.Data;

/**
 * 审核耗材采购单请求参数
 * @author 黄星星
 * @date 2019-10-06
 */

@Data
public class AuditConsumablesPurchaseOrderRequest {

    // 主键
    private Long id;

    // 审核意见
    private String auditOpinion;

    // 审核人主键
    private Long auditUserId;

    // 审核人姓名
    private String auditUserName;

}
