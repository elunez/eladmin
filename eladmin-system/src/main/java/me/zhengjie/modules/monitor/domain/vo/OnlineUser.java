package me.zhengjie.modules.monitor.domain.vo;

import lombok.Data;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Zheng Jie
 */
@Data
public class OnlineUser {

    private String userName;

    private String browser;

    private String ip;

    private String address;

    private Date createTime;

    private Date lastAccessTime;
}
