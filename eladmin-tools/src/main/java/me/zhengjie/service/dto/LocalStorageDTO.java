package me.zhengjie.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageDTO implements Serializable {

    // ID
    private Long id;

    private String realName;

    // 文件名
    private String name;

    // 后缀
    private String suffix;

    // 类型
    private String type;

    // 大小
    private String size;

    // 操作人
    private String operate;

    // 创建日期
    private Timestamp createTime;

    // 修改日期
    private Timestamp updateTime;
}