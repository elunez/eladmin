package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Data
public class DictDTO implements Serializable {

    private Long id;

    private String name;

    private String remark;
}
