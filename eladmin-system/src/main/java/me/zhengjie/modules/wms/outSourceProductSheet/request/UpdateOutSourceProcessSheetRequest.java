package me.zhengjie.modules.wms.outSourceProductSheet.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-08-17
 */
@Data
public class UpdateOutSourceProcessSheetRequest implements Serializable {
    // 委外加工公司名称
    private String outSourceCompanyName;

    // 委外加工公司编号
    private String outSourceCompanyCode;

    // 委外负责人id
    private Integer outSourceAdminId;

    // 委外负责人姓名
    private String outSourceAdminName;

    // 联系方式
    private String contactWay;

    // 委外加工单单据编号
    private String outSourceProcessSheetCode;

    // 委外加工产品
    private List<OutSourceProcessSheetProductRequest> outSourceProcessSheetProductList;
}
