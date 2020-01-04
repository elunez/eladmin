package me.zhengjie.modules.wms.bd.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;


/**
* @author huangxingxing
* @date 2020-01-04
*/
@Data
public class ProductSeriesDTO implements Serializable {

    // 主键
    private Long id;

    // 产品系列名称
    private String productSeriesName;

    // 状态
    private Boolean status;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;
}