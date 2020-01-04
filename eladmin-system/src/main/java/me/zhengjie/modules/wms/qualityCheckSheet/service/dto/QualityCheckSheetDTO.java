package me.zhengjie.modules.wms.qualityCheckSheet.service.dto;

import lombok.Data;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;


/**
* @author huangxingxing
* @date 2019-11-12
*/
@Data
public class QualityCheckSheetDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private String createTimeStr;

    private Timestamp updateTime;

    private String updateTimeStr;

    private Boolean status;

    // 制单人
    private Long makePeopleId;

    // 制单人姓名
    private String makePeopleName;

    // 质量检验单单据编号
    private String qualityCheekSheetCode;

    private String remark;

    // 委外加工单产品信息
    private List<QualityCheckSheetProductDTO> qualityCheckSheetProductList;
}