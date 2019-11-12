package me.zhengjie.modules.wms.qualityCheckSheet.request;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author 黄星星
 * @date 2019-11-12
 */
@Data
public class CreateQualityCheckSheetRequest implements Serializable {
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

    // 委外加工产品
    private List<QualityCheckSheetProductRequest> qualityCheckSheetProductList;
}
