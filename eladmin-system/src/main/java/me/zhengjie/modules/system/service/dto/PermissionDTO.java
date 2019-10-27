package me.zhengjie.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
@Getter
@Setter
public class PermissionDTO{

	private Long id;

	private String name;

	private Long pid;

	private String alias;

	private List<PermissionDTO>  children;

	private Timestamp createTime;
}
