package me.zhengjie.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Getter
@Setter
public class DictDTO{

    private Long id;

    private String name;

    private String remark;

    private Timestamp createTime;
}
