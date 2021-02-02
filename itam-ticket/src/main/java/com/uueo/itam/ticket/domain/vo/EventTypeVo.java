package com.uueo.itam.ticket.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventTypeVo {

    /** 事件类型名称 */
    private String typename;

    private Integer parentid;

    /** 层级路径 */
    private String level;

    /** 层级 */
    private Integer seq;

    /** 拼音 */
    private String pinyin;

    /** 启用标识 */
    private Integer bz;

    private List<EventTypeVo> children;
}
