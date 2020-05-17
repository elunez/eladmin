package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.io.Serializable;


/**
 * 委外亚寿诞产品信息
* @author jie
* @date 2019-10-01
*/
@Data
public class OutSourceInspectionCertificateProductDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Boolean status;

    // 所属委外验收单
    private Long outSourceInspectionCertificateId;

    private Long outSourceInspectionCertificateCode;

    private String productCode;

    private Long productId;

    private String productName;

    private String remark;

    // 产品个数
    private Integer productNumber;

    // 合格数量
    private Integer qualifiedNumber;

    // 报废数量
    private Integer scrapNumber;

    // 所属委外加工单
    private String outSourceProcessSheetCode;

}