package me.zhengjie.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 黄星星
 * @date 2020-02-26
 */
@Entity
@Getter
@Setter
@Table(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Menu.Update.class})
    private Long id;

    // 创建时间
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    // 更新时间
    @Column(name = "update_time")
    @CreationTimestamp
    private Timestamp updateTime;

    // 状态
    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_id_send")
    private Long userIdSend;

    @Column(name = "user_name_send")
    private String userNameSend;

    @Column(name = "user_id_accept")
    private Long userIdAccept;

    /**
     * 消息内容
     */
    @Column(name = "mess_content")
    private String messContent;

    /**
     * 状态
     * 0 未读
     * 1 已读
     */
    @Column(name = "read_status")
    private Integer readStatus;

    /**
     * 模块类型
     */
    @Column(name = "module_type_code")
    private String moduleTypeCode;

    @Column(name = "module_type_name")
    private String moduleTypeName;


    /**
     * 模块路径
     */
    @Column(name = "module_path")
    private String modulePath;


    /**
     * 单据编号
     */
    @Column(name = "init_code")
    private String initCode;


}
