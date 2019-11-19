package me.zhengjie.gen.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
* @author Zheng Jie
* @date 2019-11-19
*/
@Data
public class GenTestDTO implements Serializable {

    // ID
    // 处理精度丢失问题
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    // 名称
    private String name;

    // 状态
    private Boolean status;

    // 日期
    private Timestamp date;

    // 创建日期
    private Timestamp createTime;
}