package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployHistoryDTO implements Serializable {

	/**
	 * 编号
	 */
    private String id;

	/**
	 * 应用名称
	 */
    private String appName;

	/**
	 * 部署IP
	 */
    private String ip;

	/**
	 * 部署时间
	 */
    private String deployDate;

	/**
	 * 部署人员
	 */
	private String deployUser;

	/**
	 * 部署编号
	 */
	private Long deployId;
}
