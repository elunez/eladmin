package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Set;


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

	private AppDTO app;

	/**
	 * 服务器
	 */
	private Set<ServerDeployDTO> deploys;

	/**
	 * 服务状态
	 */
	private String status;

}
