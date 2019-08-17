package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author jie
* @date 2019-08-03
*/
@Data
public class CustomerInfoQueryCriteria {

    private String customerName;

    private String customerCode;

}