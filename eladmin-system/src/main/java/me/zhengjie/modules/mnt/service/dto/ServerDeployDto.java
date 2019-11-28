package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class ServerDeployDto implements Serializable {

    private Long id;

	private String name;

	private String ip;

	private Integer port;

	private String account;

	private String password;

	private Timestamp createTime;
}
