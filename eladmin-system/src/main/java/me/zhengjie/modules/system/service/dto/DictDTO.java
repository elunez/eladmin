package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.PredicateInfo;

import java.io.Serializable;

/**
* @author jie
* @date 2019-04-10
*/
@Data
public class DictDTO implements Serializable {

    private Long id;

    /**
     * 字典名称
     */
    @PredicateInfo(queryType = PredicateInfo.QueryType.INNER_LIKE)
    private String name;

    /**
     * 描述
     */
    @PredicateInfo(queryType = PredicateInfo.QueryType.INNER_LIKE)
    private String remark;
}
