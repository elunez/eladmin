package me.zhengjie.modules.wms.outSourceProductSheet.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author jie
* @date 2019-08-17
*/
@Entity
@Data
@Table(name="s_out_source_process_sheet_product")
public class OutSourceProcessSheetProduct implements Serializable {

    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 状态
    @Column(name = "status")
    private Boolean status;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    // 所属委外加工单
    @Column(name = "out_source_process_sheet_id")
    private Long outSourceProcessSheetId;

    // 产品主键
    @Column(name = "product_id",nullable = false)
    private Long productId;

    // 产品名称
    @Column(name = "product_name",nullable = false)
    private String productName;

    // 产品编号
    @Column(name = "product_code",nullable = false)
    private String productCode;

    // 所属订单
    @Column(name = "customer_order_id")
    private Long customerOrderId;

    // 委外产品数量
    @Column(name = "product_number")
    private Integer productNumber;

    // 交付日期
    @Column(name = "deliver_date")
    private String deliverDate;

    // 备注
    @Column(name = "remark")
    private String remark;

    // 制单人主键
    @Column(name = "make_people_id")
    private Long makePeopleId;

    // 制单人姓名
    @Column(name = "make_people_name")
    private String makePeopleName;

    public void copy(OutSourceProcessSheetProduct source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}