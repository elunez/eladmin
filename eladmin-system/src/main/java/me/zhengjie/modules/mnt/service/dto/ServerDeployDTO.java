package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
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
}
