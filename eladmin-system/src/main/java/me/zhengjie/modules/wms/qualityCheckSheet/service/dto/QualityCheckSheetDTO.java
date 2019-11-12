package me.zhengjie.modules.wms.qualityCheckSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author huangxingxing
* @date 2019-11-12
*/
@Data
public class QualityCheckSheetDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 质量检验单单据编号
    private String qualityCheekSheetCode;

    private String remark;
}