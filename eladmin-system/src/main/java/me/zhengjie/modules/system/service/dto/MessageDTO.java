package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 黄星星
 * @date 2020-02-27
 */
@Data
public class MessageDTO {
    private Long id;

    // 创建时间
    private Timestamp createTime;

    // 更新时间
    private Timestamp updateTime;

    private Long userIdSend;

    private Long userNameSend;

    private Long userIdAccept;

    /**
     * 消息类型
     */
    private String messContent;

    /**
     * 状态
     * 0 未读
     * 1 已读
     */
    private Integer readStatus;

    /**
     * 模块类型
     */
    private String moduleType;


    /**
     * 模块路径
     */
    private String modulePath;


    /**
     * 单据编号
     */
    private String initCode;
}
