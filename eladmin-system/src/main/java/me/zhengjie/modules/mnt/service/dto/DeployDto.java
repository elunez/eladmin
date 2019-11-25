package me.zhengjie.modules.mnt.service.dto;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployDto implements Serializable {

	/**
	 * 部署编号
	 */
    private String id;

	private AppDto app;

	/**
	 * 服务器
	 */
	private Set<ServerDeployDto> deploys;

	private String servers;

	/**
	 * 服务状态
	 */
	private String status;

	private Timestamp createTime;

	public String getServers() {
		if(CollectionUtil.isNotEmpty(deploys)){
			return deploys.stream().map(ServerDeployDto::getName).collect(Collectors.joining(","));
		}
		return servers;
	}
}
