package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-08-03
*/
@Data
public class OutSourceCompanyInfoQueryCriteria {

    // 委外公司名称
    private String outSourceCompanyName;

    // 委外公司编号
    private String outSourceCompanyCode;
}