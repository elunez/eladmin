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
public class DictDetailDTO{

    private Long id;

    private String label;

    private String value;

    private String sort;

    private Timestamp createTime;
}