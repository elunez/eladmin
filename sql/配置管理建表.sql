-- 配置文件组
drop table if exists config_file_group;
CREATE TABLE `config_file_group` (
  `pk_config_file_group` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置文件组主键',
  `name` varchar(255) NOT NULL COMMENT '配置文件组名称',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_config_file_group`) USING BTREE
) ENGINE=InnoDB COMMENT='配置文件组';

-- 配置文件
drop table if exists config_file;
CREATE TABLE `config_file` (
  `pk_config_file` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置文件主键',
  `pk_config_file_group` bigint(20) DEFAULT NULL COMMENT '配置文件组主键',
	`name` varchar(255) NOT NULL COMMENT '配置文件名称',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_config_file`) USING BTREE,
  KEY `inx_pk_config_file_group` (`pk_config_file_group`)
) ENGINE=InnoDB COMMENT='配置文件';

-- 配置项组
drop table if exists config_group;
CREATE TABLE `config_group` (
  `pk_config_group` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置项组主键',
	`pk_config_file` bigint(20) DEFAULT NULL COMMENT '配置文件主键',
  `name` varchar(255) NOT NULL COMMENT '配置项组名称',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_config_group`) USING BTREE,
	KEY `inx_pk_config_file` (`pk_config_file`)
) ENGINE=InnoDB COMMENT='配置项组';

-- 配置项
drop table if exists config_item;
CREATE TABLE `config_item` (
  `pk_config_item` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置项主键',
	`pk_config_group` bigint(20) DEFAULT NULL COMMENT '配置项组主键',
  `config_key` varchar(255) NOT NULL COMMENT '配置项键',
	`config_value` varchar(255) NOT NULL COMMENT '配置项值',
	`config_desc` varchar(255) NOT NULL COMMENT '配置项描述',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_config_item`) USING BTREE,
	KEY `inx_pk_config_group` (`pk_config_group`)
) ENGINE=InnoDB COMMENT='配置项';