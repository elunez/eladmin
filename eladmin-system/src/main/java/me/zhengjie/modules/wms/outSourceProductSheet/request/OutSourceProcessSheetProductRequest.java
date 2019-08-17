package me.zhengjie.modules.wms.outSourceProductSheet.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-08-17
 */
@Data
public class OutSourceProcessSheetProductRequest implements Serializable {
    // 所属委外加工单
    private Long outSourceProcessSheetId;

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
