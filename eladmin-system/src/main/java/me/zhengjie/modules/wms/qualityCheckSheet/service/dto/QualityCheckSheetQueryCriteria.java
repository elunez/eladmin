package me.zhengjie.modules.wms.qualityCheckSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author huangxingxing
* @date 2019-11-12
*/
@Data
public class QualityCheckSheetQueryCriteria{

    // 质量检验单单据编号
    private String qualityCheekSheetCode;

}