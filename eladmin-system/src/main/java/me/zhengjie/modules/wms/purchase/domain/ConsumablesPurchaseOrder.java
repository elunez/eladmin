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
@Table(name="consumables_purchase_order")
public class ConsumablesPurchaseOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    // 采购人主键
    @Column(name = "purchase_user_id")
    private Long purchaseUserId;

    //  采购人姓名 
    @Column(name = "purchase_user_name")
    private String purchaseUserName;

    // 状态
    @Column(name = "status")
    private Boolean status;

    // 审核状态
    @Column(name = "audit_status")
    private Integer auditStatus;

    @Column(name = "audit_user_id")
    private Long auditUserId;

    // 审核人姓名
    @Column(name = "audit_user_name")
    private String auditUserName;

    // 耗材采购单单据编号
    @Column(name = "consumables_purchase_order_code")
    private String consumablesPurchaseOrderCode;

    public void copy(ConsumablesPurchaseOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}