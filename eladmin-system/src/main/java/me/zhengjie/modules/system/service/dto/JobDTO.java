package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Data
@NoArgsConstructor
public class JobDTO implements Serializable {

    private Long id;

    private Long sort;

    private String name;

    private Boolean enabled;

    private DeptDTO dept;

    private String deptSuperiorName;

    private Timestamp createTime;

    public JobDTO(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}