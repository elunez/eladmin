package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import me.zhengjie.modules.mnt.service.ServerAccountService;
import me.zhengjie.modules.mnt.service.ServerDeployService;
import me.zhengjie.utils.SpringContextHolder;

import java.io.Serializable;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class ServerDeployDTO implements Serializable {

	/**
	 * 服务器IP
	 */
    private String id;

	/**
	 * 服务器账号
	 */
    private String accountId;

	/**
	 * 账号名称
	 */
	private String accountName;

	public String getAccountName() {
		if(accountId != null){
			ServerAccountService serverAccountService = SpringContextHolder.getBean(ServerAccountService.class);
			return serverAccountService.findById(accountId).getName();
		}
		return accountName;
	}
}
