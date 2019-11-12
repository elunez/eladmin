package me.zhengjie.modules.wms.qualityCheckSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author huangxingxing
* @date 2019-11-12
*/
@Data
public class QualityCheckSheetProductDTO implements Serializable {

    // 主键
    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer status;

    // 所属质量检验单
    private Long qualityCheckSheetId;

    private String productCode;

    private Long productId;

    private String productName;

    private String remark;

    // 产品个数
    private Integer productNumber;

    // 合格个数
    private Integer qualifiedNumber;

    // 报废数量
    private Integer scrapNumber;
}