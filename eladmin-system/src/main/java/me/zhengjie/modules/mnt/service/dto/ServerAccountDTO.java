package me.zhengjie.modules.mnt.service.dto;

import lombok.Data;
import java.io.Serializable;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class ServerAccountDTO implements Serializable {

	/**
	 * 编号
	 */
    private String id;

	/**
	 * 名称
	 */
    private String name;

	/**
	 * 账号
	 */
    private String account;

	/**
	 * 密码
	 */
    private String password;
}
