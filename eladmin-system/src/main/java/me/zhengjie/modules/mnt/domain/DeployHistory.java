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
@Table(name="mnt_deploy_history")
public class DeployHistory implements Serializable {

	/**
	 * 编号
	 */
    @Id
    @Column(name = "id")
    private String id;

	/**
	 * 应用名称
	 */
    @Column(name = "app_name",nullable = false)
    private String appName;

	/**
	 * 部署IP
	 */
    @Column(name = "ip",nullable = false)
    private String ip;

	/**
	 * 部署时间
	 */
    @Column(name = "deploy_date",nullable = false)
    private String deployDate;

	/**
	 * 部署人员
	 */
    @Column(name = "deploy_user",nullable = false)
    private String deployUser;

	/**
	 * 部署编号
	 */
	@Column(name = "deploy_id",nullable = false)
	private String deployId;

    public void copy(DeployHistory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
