package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-01
*/
@Data
public class OutSourceInspectionCertificateProductDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    // 所属委外验收单
    private Long outSourceInspectionCertificateId;

    private String productCode;

    private Long productId;

    private String productName;

    private String remark;
}