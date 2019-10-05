package me.zhengjie.modules.wms.purchase.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-06
*/
@Data
public class ConsumablesPurchaseOrderProductDTO implements Serializable {

    private Long id;

    // 所属耗材采购单
    private Long consumablesPurchaseOrderId;

    // 耗材主键
    private Long consumablesId;

    // 耗材名称
    private String consumablesName;

    // 产品单价
    private Long unitPrice;

    // 产品总价
    private Long totalPrice;

    // 产品数量
    private Long consumablesNumber;

    // 备注
    private String remark;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    private Integer status;

    // 耗材编号
    private String consumablesCode;
}