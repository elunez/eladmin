package me.zhengjie.modules.mnt.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Entity
@Data
@Table(name="mnt_server_account")
public class ServerAccount implements Serializable {

	/**
	 * 编号
	 */
    @Id
    @Column(name = "id")
    private String id;

	/**
	 * 名称
	 */
    @Column(name = "name")
    private String name;

	/**
	 * 账号
	 */
    @Column(name = "account")
    private String account;

	/**
	 * 密码
	 */
    @Column(name = "password")
    private String password;

    public void copy(ServerAccount source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
