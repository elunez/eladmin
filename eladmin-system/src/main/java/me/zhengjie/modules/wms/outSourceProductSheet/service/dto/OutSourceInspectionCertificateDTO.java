package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-01
*/
@Data
public class OutSourceInspectionCertificateDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    // 所属委外加工单
    private Long outSourceProcessSheetId;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 委外加工验收单单据编号
    private String outSourceInspectionCertificateCode;

    private String remark;
}