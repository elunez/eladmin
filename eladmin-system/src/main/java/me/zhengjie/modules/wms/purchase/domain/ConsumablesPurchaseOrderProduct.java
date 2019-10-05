package me.zhengjie.modules.wms.purchase.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-10-06
*/
@Entity
@Data
@Table(name="consumables_purchase_order_product")
public class ConsumablesPurchaseOrderProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 所属耗材采购单
    @Column(name = "consumables_purchase_order_id")
    private Long consumablesPurchaseOrderId;

    // 耗材主键
    @Column(name = "consumables_id")
    private Long consumablesId;

    // 耗材名称
    @Column(name = "consumables_name")
    private String consumablesName;

    // 产品单价
    @Column(name = "unit_price")
    private Long unitPrice;

    // 产品总价
    @Column(name = "total_price")
    private Long totalPrice;

    // 产品数量
    @Column(name = "consumables_number")
    private Long consumablesNumber;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 创建时间
    @Column(name = "create_time")
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "status")
    private Integer status;

    // 耗材编号
    @Column(name = "consumables_code")
    private String consumablesCode;

    public void copy(ConsumablesPurchaseOrderProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}