
package me.zhengjie.modules.wms.customerOrder.service.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 黄星星
 * @date 2019-08-17
 */
@Data
public class CustomerOrderProductDTO {

    private Long id;

    // 所属客户订单
    private Long customerOrderId;

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

    // 状态
    private Boolean status;

    // 产品编号
    private String productCode;
}
