package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-17
*/
@Data
public class OutSourceProcessSheetProductDTO implements Serializable {

    // 主键
    private Long id;

    // 状态
    private Boolean status;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    // 所属委外加工单
    private Long outSourceProcessSheetId;

    private String  outSourceProcessSheetCode;

    // 产品主键
    private Long productId;

    // 产品名称
    private String productName;

    // 产品编号
    private String productCode;

    // 所属订单
    private Long customerOrderId;

    // 委外产品数量
    private Integer productNumber;

    // 交付日期
    private String deliverDate;

    // 备注
    private String remark;

    // 制单人主键
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;
}