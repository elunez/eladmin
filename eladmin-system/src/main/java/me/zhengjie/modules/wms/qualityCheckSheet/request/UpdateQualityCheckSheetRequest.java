package me.zhengjie.modules.wms.qualityCheckSheet.request;

import lombok.Data;
import me.zhengjie.modules.wms.outSourceProductSheet.service.dto.OutSourceProcessSheetProductDTO;
import me.zhengjie.modules.wms.qualityCheckSheet.service.dto.QualityCheckSheetProductDTO;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-11-12
 */
@Data
public class UpdateQualityCheckSheetRequest implements Serializable {

    // 质量检验单主键
    private Long id;

    // 制单人
    @Column(name = "make_people_id")
    private Long makePeopleId;

    // 制单人姓名
    @Column(name = "make_people_name")
    private String makePeopleName;

    // 质量检验单单据编号
    @Column(name = "quality_cheek_sheet_code")
    private String qualityCheekSheetCode;

    @Column(name = "remark")
    private String remark;

    // 质量检验单产品
    private List<QualityCheckSheetProductDTO> qualityCheckSheetProductList;
}
