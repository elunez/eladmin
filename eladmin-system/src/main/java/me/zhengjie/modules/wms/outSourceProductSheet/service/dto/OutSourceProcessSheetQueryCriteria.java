package me.zhengjie.modules.wms.outSourceProductSheet.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-08-17
*/
@Data
public class OutSourceProcessSheetQueryCriteria {

    // 委外加工单单据编号
    private String outSourceProcessSheetCode;

    // 委外公司
    private String outSourceCompanyName;

    // 委外公司负责人
    private String outSourceAdminName;
}