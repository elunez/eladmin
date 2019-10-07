package me.zhengjie.modules.wms.purchase.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
* @author jie
* @date 2019-10-06
*/
@Data
public class ProductPurchaseOrderDTO implements Serializable {

    private Long id;

    private Timestamp createTime;

    private String createTimeStr;

    private Timestamp updateTime;

    // 采购人主键
    private Long purchaseUserId;

    //  采购人姓名 
    private String purchaseUserName;

    // 状态
    private Boolean status;

    // 审核状态
    private String auditStatus;

    private String auditStatusName;

    private Long auditUserId;

    // 审核人姓名
    private String auditUserName;

    private String productPurchaseOrderCode;

    private String auditOpinion;

    private Date auditTime;

    //产品采购单产品信息
    private List<ProductPurchaseOrderProductDTO> productPurchaseOrderProductList;
}