package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployDTO implements Serializable {

	/**
	 * 部署编号
	 */
    private String id;

	/**
	 * 应用编号
	 */
    private String appId;

	/**
	 * IP列表
	 */
    private String ip;


	/**
	 * 服务状态
	 */
	private String status;

}
