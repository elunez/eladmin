package me.zhengjie.modules.wms.qualityCheckSheet.request;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author 黄星星
 * @date 2019-11-12
 */
@Data
public class QualityCheckSheetProductRequest implements Serializable {

    // 所属质量检验单
    @Column(name = "quality_check_sheet_id")
    private Long qualityCheckSheetId;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "remark")
    private String remark;

    // 产品个数
    @Column(name = "product_number")
    private Integer productNumber;

    // 合格个数
    @Column(name = "qualified_number")
    private Integer qualifiedNumber;

    // 报废数量
    @Column(name = "scrap_number")
    private Integer scrapNumber;
}
