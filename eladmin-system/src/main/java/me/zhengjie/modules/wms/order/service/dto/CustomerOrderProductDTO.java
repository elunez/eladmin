package me.zhengjie.modules.wms.order.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author jie
* @date 2019-08-03
*/
@Data
public class CustomerOrderProductDTO implements Serializable {

    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 所属客户订单
    private Long customerOrderId;

    // 产品主键
    private Long productId;

    // 产品名称
    private Long productName;

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
}