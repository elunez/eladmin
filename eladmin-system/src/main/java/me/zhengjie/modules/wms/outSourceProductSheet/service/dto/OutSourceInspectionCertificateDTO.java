package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;


/**
 * 委外验收单信息
* @author jie
* @date 2019-10-01
*/
@Data
public class OutSourceInspectionCertificateDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Boolean status;

    // 所属委外加工单
    private String outSourceProcessSheetCode;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 委外加工验收单单据编号
    private String outSourceInspectionCertificateCode;

    private String remark;

    // 委外验收单产品信息
    private List<OutSourceInspectionCertificateProductDTO> outSourceInspectionCertificateProductList;
}