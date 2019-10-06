package me.zhengjie.modules.wms.purchase.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 新增产品采购单请求产品信息
* @author jie
* @date 2019-10-06
*/
@Data
public class ProductPurchaseOrderProductRequest implements Serializable {

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

    private Boolean status;

    // 产品编号
    private String productCode;

    public void copy(ProductPurchaseOrderProductRequest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}