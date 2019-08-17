package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-08-17
*/
@Data
public class OutSourceProcessSheetDTO implements Serializable {

    private Long id;

    // 状态
    private Integer status;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

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
}