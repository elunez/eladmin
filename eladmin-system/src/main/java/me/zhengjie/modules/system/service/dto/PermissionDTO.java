package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Data
public class PermissionDTO implements Serializable{

	private Long id;

	private String name;

	private Long pid;

	private String alias;

	private Timestamp createTime;

	private List<PermissionDTO>  children;

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pid=" + pid +
				", alias='" + alias + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
