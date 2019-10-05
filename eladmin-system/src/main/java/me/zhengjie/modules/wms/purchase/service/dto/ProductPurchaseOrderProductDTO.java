package me.zhengjie.modules.wms.purchase.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-06
*/
@Data
public class ProductPurchaseOrderProductDTO implements Serializable {

    private Long id;

    // 所属产品采购单
    private Long productPurchaseOrderId;

    // 产品主键
    private Long productId;

    // 产品名称
    private String productName;

    // 规格
    private String specifications;

    // 产品单价
    private Long unitPrice;

    // 产品总价
    private Long totalPrice;

    // 产品数量
    private Long productNumber;

    // 备注
    private String remark;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    private Integer status;

    // 产品编号
    private String productCode;
}