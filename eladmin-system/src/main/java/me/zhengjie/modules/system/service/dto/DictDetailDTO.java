package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
@Data
public class DictDetailDTO implements Serializable {

    private Long id;

    private String label;

    private String value;

    private String sort;
}