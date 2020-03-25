package me.zhengjie.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Getter
@Setter
public class DictDto implements Serializable {

    private Long id;

    private String name;

    private String remark;

    private List<DictDetailDto> dictDetails;

    private Timestamp createTime;
}
