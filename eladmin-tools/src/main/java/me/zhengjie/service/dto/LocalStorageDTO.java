package me.zhengjie.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Getter
@Setter
public class LocalStorageDTO  implements Serializable {

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

    private Timestamp createTime;
}