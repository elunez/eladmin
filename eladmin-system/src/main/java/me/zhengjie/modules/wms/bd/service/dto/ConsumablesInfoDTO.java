package me.zhengjie.modules.wms.bd.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author jie
* @date 2019-10-05
*/
@Data
public class ConsumablesInfoDTO implements Serializable {

    private Long id;

    private Timestamp createTime;

    private Timestamp updateTime;

    // 耗材名称
    private String consumablesName;

    private Boolean status;
}