/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 110206 (11.2.6-MariaDB)
 Source Host           : localhost:3306
 Source Schema         : eladmin

 Target Server Type    : MySQL
 Target Server Version : 110206 (11.2.6-MariaDB)
 File Encoding         : 65001

 Date: 15/01/2025 18:20:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_column
-- ----------------------------
DROP TABLE IF EXISTS `code_column`;
CREATE TABLE `code_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(180) DEFAULT NULL COMMENT 'Table Name',
  `column_name` varchar(255) DEFAULT NULL COMMENT 'Database Field Name',
  `column_type` varchar(255) DEFAULT NULL COMMENT 'Database Field Type',
  `dict_name` varchar(255) DEFAULT NULL COMMENT 'Dictionary Name',
  `extra` varchar(255) DEFAULT NULL COMMENT 'Additional Field Parameters',
  `form_show` bit(1) DEFAULT NULL COMMENT 'Show in Form',
  `form_type` varchar(255) DEFAULT NULL COMMENT 'Form Type',
  `key_type` varchar(255) DEFAULT NULL COMMENT 'Database Field Key Type',
  `list_show` bit(1) DEFAULT NULL COMMENT 'Show in List',
  `not_null` bit(1) DEFAULT NULL COMMENT 'Not Null',
  `query_type` varchar(255) DEFAULT NULL COMMENT 'Query Type',
  `remark` varchar(255) DEFAULT NULL COMMENT 'Description',
  `date_annotation` varchar(255) DEFAULT NULL COMMENT 'Date Annotation',
  PRIMARY KEY (`column_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Code Generation Field Information Storage';

-- ----------------------------
-- Records of code_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for code_config
-- ----------------------------
DROP TABLE IF EXISTS `code_config`;
CREATE TABLE `code_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) DEFAULT NULL COMMENT 'Table Name',
  `author` varchar(255) DEFAULT NULL COMMENT 'Author',
  `cover` bit(1) DEFAULT NULL COMMENT 'Override',
  `module_name` varchar(255) DEFAULT NULL COMMENT 'Module Name',
  `pack` varchar(255) DEFAULT NULL COMMENT 'Package Location',
  `path` varchar(255) DEFAULT NULL COMMENT 'Frontend Code Generation Path',
  `api_path` varchar(255) DEFAULT NULL COMMENT 'Frontend API File Path',
  `prefix` varchar(255) DEFAULT NULL COMMENT 'Table Prefix',
  `api_alias` varchar(255) DEFAULT NULL COMMENT 'Interface Name',
  PRIMARY KEY (`config_id`) USING BTREE,
  KEY `idx_table_name` (`table_name`(100))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Code Generator Configuration';

-- ----------------------------
-- Records of code_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_app
-- ----------------------------
DROP TABLE IF EXISTS `mnt_app`;
CREATE TABLE `mnt_app` (
  `app_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT 'Application Name',
  `upload_path` varchar(255) DEFAULT NULL COMMENT 'Upload Directory',
  `deploy_path` varchar(255) DEFAULT NULL COMMENT 'Deployment Path',
  `backup_path` varchar(255) DEFAULT NULL COMMENT 'Backup Path',
  `port` int(255) DEFAULT NULL COMMENT 'Application Port',
  `start_script` varchar(4000) DEFAULT NULL COMMENT 'Start Script',
  `deploy_script` varchar(4000) DEFAULT NULL COMMENT 'Deployment Script',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Application Management';

-- ----------------------------
-- Records of mnt_app
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_database
-- ----------------------------
DROP TABLE IF EXISTS `mnt_database`;
CREATE TABLE `mnt_database` (
  `db_id` varchar(50) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT 'Name',
  `jdbc_url` varchar(255) NOT NULL COMMENT 'JDBC Connection',
  `user_name` varchar(255) NOT NULL COMMENT 'Username',
  `pwd` varchar(255) NOT NULL COMMENT 'Password',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`db_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Database Management';

-- ----------------------------
-- Records of mnt_database
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy`;
CREATE TABLE `mnt_deploy` (
  `deploy_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` bigint(20) DEFAULT NULL COMMENT 'Application ID',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`deploy_id`) USING BTREE,
  KEY `idx_app_id` (`app_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Deployment Management';

-- ----------------------------
-- Records of mnt_deploy
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy_history
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_history`;
CREATE TABLE `mnt_deploy_history` (
  `history_id` varchar(50) NOT NULL COMMENT 'ID',
  `app_name` varchar(255) NOT NULL COMMENT 'Application Name',
  `deploy_date` datetime NOT NULL COMMENT 'Deployment Date',
  `deploy_user` varchar(50) NOT NULL COMMENT 'Deployment User',
  `ip` varchar(20) NOT NULL COMMENT 'Server IP',
  `deploy_id` bigint(20) DEFAULT NULL COMMENT 'Deployment ID',
  PRIMARY KEY (`history_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Deployment History Management';

-- ----------------------------
-- Records of mnt_deploy_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_deploy_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_server`;
CREATE TABLE `mnt_deploy_server` (
  `deploy_id` bigint(20) NOT NULL COMMENT 'Deployment ID',
  `server_id` bigint(20) NOT NULL COMMENT 'Server ID',
  PRIMARY KEY (`deploy_id`,`server_id`) USING BTREE,
  KEY `idx_deploy_id` (`deploy_id`),
  KEY `idx_server_id` (`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Application and Server Association';

-- ----------------------------
-- Records of mnt_deploy_server
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for mnt_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server`;
CREATE TABLE `mnt_server` (
  `server_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(50) DEFAULT NULL COMMENT 'Account',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP Address',
  `name` varchar(100) DEFAULT NULL COMMENT 'Name',
  `password` varchar(100) DEFAULT NULL COMMENT 'Password',
  `port` int(11) DEFAULT NULL COMMENT 'Port',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`server_id`) USING BTREE,
  KEY `idx_ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Server Management';

-- ----------------------------
-- Records of mnt_server
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT 'Parent Department',
  `sub_count` int(5) DEFAULT 0 COMMENT 'Number of Sub-departments',
  `name` varchar(255) NOT NULL COMMENT 'Name',
  `dept_sort` int(5) DEFAULT 999 COMMENT 'Sort Order',
  `enabled` bit(1) NOT NULL COMMENT 'Status',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`dept_id`) USING BTREE,
  KEY `idx_pid` (`pid`),
  KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Department';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, 7, 1, 'R&D Department', 3, b'1', 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (5, 7, 0, 'Operations Department', 4, b'1', 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (6, 8, 0, 'Testing Department', 6, b'1', 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (7, NULL, 2, 'South China Branch', 0, b'1', 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (8, NULL, 2, 'North China Branch', 1, b'1', 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (15, 8, 0, 'UI Department', 7, b'1', 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO `sys_dept` (`dept_id`, `pid`, `sub_count`, `name`, `dept_sort`, `enabled`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (17, 2, 0, 'Development Group 1', 999, b'1', 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT 'Dictionary Name',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Data Dictionary';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`dict_id`, `name`, `description`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, 'user_status', 'User Status', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` (`dict_id`, `name`, `description`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (4, 'dept_status', 'Department Status', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` (`dict_id`, `name`, `description`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (5, 'job_status', 'Job Status', NULL, 'admin', '2019-10-27 20:31:36', '2025-01-14 15:48:29');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail` (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_id` bigint(11) DEFAULT NULL COMMENT 'Dictionary ID',
  `label` varchar(255) NOT NULL COMMENT 'Dictionary Label',
  `value` varchar(255) NOT NULL COMMENT 'Dictionary Value',
  `dict_sort` int(5) DEFAULT NULL COMMENT 'Sort Order',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`detail_id`) USING BTREE,
  KEY `idx_dict_id` (`dict_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Data Dictionary Details';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, 1, 'Active', 'true', 1, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, 1, 'Disabled', 'false', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (3, 4, 'Enabled', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (4, 4, 'Disabled', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (5, 5, 'Enabled', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` (`detail_id`, `dict_id`, `label`, `value`, `dict_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (6, 5, 'Disabled', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(180) NOT NULL COMMENT 'Job Name',
  `enabled` bit(1) NOT NULL COMMENT 'Job Status',
  `job_sort` int(5) DEFAULT NULL COMMENT 'Sort Order',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`job_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Job';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` (`job_id`, `name`, `enabled`, `job_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (8, 'HR Specialist', b'1', 3, NULL, NULL, '2019-03-29 14:52:28', NULL);
INSERT INTO `sys_job` (`job_id`, `name`, `enabled`, `job_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (10, 'Product Manager', b'1', 4, NULL, NULL, '2019-03-29 14:55:51', NULL);
INSERT INTO `sys_job` (`job_id`, `name`, `enabled`, `job_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (11, 'Full Stack Developer', b'1', 2, NULL, 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `sys_job` (`job_id`, `name`, `enabled`, `job_sort`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (12, 'Software Tester', b'1', 5, NULL, 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `log_type` varchar(10) NOT NULL COMMENT 'Log Type: INFO/ERROR',
  `method` varchar(255) DEFAULT NULL COMMENT 'Method Name',
  `params` text DEFAULT NULL COMMENT 'Parameters',
  `request_ip` varchar(255) DEFAULT NULL COMMENT 'Request IP',
  `time` bigint(20) DEFAULT NULL COMMENT 'Execution Time',
  `username` varchar(255) DEFAULT NULL COMMENT 'Username',
  `address` varchar(255) DEFAULT NULL COMMENT 'Address',
  `browser` varchar(255) DEFAULT NULL COMMENT 'Browser',
  `exception_detail` text DEFAULT NULL COMMENT 'Exception',
  `create_time` datetime NOT NULL COMMENT 'Creation Time',
  PRIMARY KEY (`log_id`) USING BTREE,
  KEY `idx_create_time_index` (`create_time`),
  KEY `idx_log_type` (`log_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3636 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='System Log';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT 'Parent Menu ID',
  `sub_count` int(5) DEFAULT 0 COMMENT 'Number of Sub-menus',
  `type` int(11) DEFAULT NULL COMMENT 'Menu Type',
  `title` varchar(100) DEFAULT NULL COMMENT 'Menu Title',
  `name` varchar(100) DEFAULT NULL COMMENT 'Component Name',
  `component` varchar(255) DEFAULT NULL COMMENT 'Component',
  `menu_sort` int(5) DEFAULT NULL COMMENT 'Sort Order',
  `icon` varchar(255) DEFAULT NULL COMMENT 'Icon',
  `path` varchar(255) DEFAULT NULL COMMENT 'Link Address',
  `i_frame` bit(1) DEFAULT NULL COMMENT 'Is External Link',
  `cache` bit(1) DEFAULT b'0' COMMENT 'Cache',
  `hidden` bit(1) DEFAULT b'0' COMMENT 'Hidden',
  `permission` varchar(255) DEFAULT NULL COMMENT 'Permission',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Date',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  UNIQUE KEY `uniq_title` (`title`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='System Menu';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, NULL, 7, 0, 'System Management', NULL, NULL, 1, 'system', 'system', b'0', b'0', b'0', NULL, NULL, 'admin', '2018-12-18 15:11:29', '2025-01-14 15:48:18');
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, 1, 3, 1, 'User Management', 'User', 'system/user/index', 2, 'peoples', 'user', b'0', b'0', b'0', 'user:list', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (3, 1, 3, 1, 'Role Management', 'Role', 'system/role/index', 3, 'role', 'role', b'0', b'0', b'0', 'roles:list', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (5, 1, 3, 1, 'Menu Management', 'Menu', 'system/menu/index', 5, 'menu', 'menu', b'0', b'0', b'0', 'menu:list', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (6, NULL, 5, 0, 'System Monitoring', NULL, NULL, 10, 'monitor', 'monitor', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (7, 6, 0, 1, 'Operation Log', 'Log', 'monitor/log/index', 11, 'log', 'logs', b'0', b'1', b'0', NULL, NULL, 'admin', '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (9, 6, 0, 1, 'SQL Monitoring', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (10, NULL, 5, 0, 'Component Management', NULL, NULL, 50, 'zujian', 'components', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:16', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (11, 10, 0, 1, 'Icon Library', 'Icons', 'components/icons/index', 51, 'icon', 'icon', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:49', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (14, 36, 0, 1, 'Email Tool', 'Email', 'tools/email/index', 35, 'email', 'email', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (15, 10, 0, 1, 'Rich Text', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 11:58:25', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (18, 36, 3, 1, 'Storage Management', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', b'0', b'0', b'0', 'storage:list', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (19, 36, 0, 1, 'Alipay Tool', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-31 14:52:38', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (21, NULL, 2, 0, 'Multi-level Menu', NULL, '', 900, 'menu', 'nested', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (22, 21, 2, 0, 'Second Level Menu 1', NULL, '', 999, 'menu', 'menu1', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (23, 21, 0, 1, 'Second Level Menu 2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:23:57', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (24, 22, 0, 1, 'Third Level Menu 1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (27, 22, 0, 1, 'Third Level Menu 2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-07 17:27:32', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (28, 1, 3, 1, 'Task Scheduling', 'Timing', 'system/timing/index', 999, 'timing', 'timing', b'0', b'0', b'0', 'timing:list', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (30, 36, 0, 1, 'Code Generation', 'GeneratorIndex', 'generator/index', 32, 'dev', 'generator', b'0', b'1', b'0', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (32, 6, 0, 1, 'Exception Log', 'ErrorLog', 'monitor/log/errorLog', 12, 'error', 'errorLog', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-13 13:49:03', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (34, 10, 0, 1, 'Yaml Editor', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (35, 1, 3, 1, 'Department Management', 'Dept', 'system/dept/index', 6, 'dept', 'dept', b'0', b'0', b'0', 'dept:list', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (36, NULL, 6, 0, 'System Tools', NULL, '', 30, 'sys-tools', 'sys-tools', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 10:57:35', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (37, 1, 3, 1, 'Job Management', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', b'0', b'0', b'0', 'job:list', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (39, 1, 3, 1, 'Dictionary Management', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', b'0', b'0', b'0', 'dict:list', NULL, NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (41, 6, 0, 1, 'Online User', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', b'0', b'0', b'0', NULL, NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (44, 2, 0, 2, 'User Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'user:add', NULL, NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (45, 2, 0, 2, 'User Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (46, 2, 0, 2, 'User Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'user:del', NULL, NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (48, 3, 0, 2, 'Role Create', NULL, '', 2, '', '', b'0', b'0', b'0', 'roles:add', NULL, NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (49, 3, 0, 2, 'Role Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (50, 3, 0, 2, 'Role Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'roles:del', NULL, NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (52, 5, 0, 2, 'Menu Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (53, 5, 0, 2, 'Menu Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (54, 5, 0, 2, 'Menu Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (56, 35, 0, 2, 'Department Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (57, 35, 0, 2, 'Department Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (58, 35, 0, 2, 'Department Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (60, 37, 0, 2, 'Job Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'job:add', NULL, NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (61, 37, 0, 2, 'Job Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (62, 37, 0, 2, 'Job Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'job:del', NULL, NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (64, 39, 0, 2, 'Dictionary Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (65, 39, 0, 2, 'Dictionary Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (66, 39, 0, 2, 'Dictionary Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (73, 28, 0, 2, 'Task Add', NULL, '', 2, '', '', b'0', b'0', b'0', 'timing:add', NULL, NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (74, 28, 0, 2, 'Task Edit', NULL, '', 3, '', '', b'0', b'0', b'0', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (75, 28, 0, 2, 'Task Delete', NULL, '', 4, '', '', b'0', b'0', b'0', 'timing:del', NULL, NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (77, 18, 0, 2, 'Upload File', NULL, '', 2, '', '', b'0', b'0', b'0', 'storage:add', NULL, NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (78, 18, 0, 2, 'Edit File', NULL, '', 3, '', '', b'0', b'0', b'0', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (79, 18, 0, 2, 'Delete File', NULL, '', 4, '', '', b'0', b'0', b'0', 'storage:del', NULL, NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (80, 6, 0, 1, 'Server Monitoring', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', b'0', b'0', b'0', 'monitor:list', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (82, 36, 0, 1, 'Generate Configuration', 'GeneratorConfig', 'generator/config', 33, 'dev', 'generator/config/:tableName', b'0', b'1', b'1', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (83, 10, 0, 1, 'Chart Library', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', b'0', b'1', b'0', '', NULL, NULL, '2019-11-21 09:04:32', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (90, NULL, 5, 1, 'Operation and Maintenance', 'Mnt', '', 20, 'mnt', 'mnt', b'0', b'0', b'0', NULL, NULL, NULL, '2019-11-09 10:31:08', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (92, 90, 3, 1, 'Server', 'ServerDeploy', 'maint/server/index', 22, 'server', 'maint/serverDeploy', b'0', b'0', b'0', 'serverDeploy:list', NULL, NULL, '2019-11-10 10:29:25', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (93, 90, 3, 1, 'Application Management', 'App', 'maint/app/index', 23, 'app', 'maint/app', b'0', b'0', b'0', 'app:list', NULL, NULL, '2019-11-10 11:05:16', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (94, 90, 3, 1, 'Deployment Management', 'Deploy', 'maint/deploy/index', 24, 'deploy', 'maint/deploy', b'0', b'0', b'0', 'deploy:list', NULL, NULL, '2019-11-10 15:56:55', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (97, 90, 1, 1, 'Deployment Backup', 'DeployHistory', 'maint/deployHistory/index', 25, 'backup', 'maint/deployHistory', b'0', b'0', b'0', 'deployHistory:list', NULL, NULL, '2019-11-10 16:49:44', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (98, 90, 3, 1, 'Database Management', 'Database', 'maint/database/index', 26, 'database', 'maint/database', b'0', b'0', b'0', 'database:list', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (102, 97, 0, 2, 'Delete', NULL, '', 999, '', '', b'0', b'0', b'0', 'deployHistory:del', NULL, NULL, '2019-11-17 09:32:48', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (103, 92, 0, 2, 'Server Add', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:add', NULL, NULL, '2019-11-17 11:08:33', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (104, 92, 0, 2, 'Server Edit', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:edit', NULL, NULL, '2019-11-17 11:08:57', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (105, 92, 0, 2, 'Server Delete', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:del', NULL, NULL, '2019-11-17 11:09:15', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (106, 93, 0, 2, 'Application Add', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:add', NULL, NULL, '2019-11-17 11:10:03', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (107, 93, 0, 2, 'Application Edit', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:edit', NULL, NULL, '2019-11-17 11:10:28', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (108, 93, 0, 2, 'Application Delete', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:del', NULL, NULL, '2019-11-17 11:10:55', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (109, 94, 0, 2, 'Deployment Add', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:add', NULL, NULL, '2019-11-17 11:11:22', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (110, 94, 0, 2, 'Deployment Edit', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:edit', NULL, NULL, '2019-11-17 11:11:41', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (111, 94, 0, 2, 'Deployment Delete', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:del', NULL, NULL, '2019-11-17 11:12:01', NULL);
INSERT INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (116, 36, 0, 1, 'Generate Preview', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:tableName', b'0', b'1', b'1', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'Spring Bean Name',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'Cron Expression',
  `is_pause` bit(1) DEFAULT NULL COMMENT 'Status: 1 Paused, 0 Enabled',
  `job_name` varchar(255) DEFAULT NULL COMMENT 'Job Name',
  `method_name` varchar(255) DEFAULT NULL COMMENT 'Method Name',
  `params` varchar(255) DEFAULT NULL COMMENT 'Parameters',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `person_in_charge` varchar(100) DEFAULT NULL COMMENT 'Person in Charge',
  `email` varchar(100) DEFAULT NULL COMMENT 'Alert Email',
  `sub_task` varchar(100) DEFAULT NULL COMMENT 'Sub-task ID',
  `pause_after_failure` bit(1) DEFAULT NULL COMMENT 'Pause After Failure',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Date',
  PRIMARY KEY (`job_id`) USING BTREE,
  KEY `idx_is_pause` (`is_pause`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Scheduled Task';

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_quartz_job` (`job_id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`, `description`, `person_in_charge`, `email`, `sub_task`, `pause_after_failure`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, 'testTask', '0/5 * * * * ?', b'1', 'Test 1', 'run1', 'test', 'Test with parameters, use JSON for multiple parameters', 'Test', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job` (`job_id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`, `description`, `person_in_charge`, `email`, `sub_task`, `pause_after_failure`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (3, 'testTask', '0/5 * * * * ?', b'1', 'Test', 'run', '', 'Test without parameters', 'Zheng Jie', '', '6', b'1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job` (`job_id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`, `description`, `person_in_charge`, `email`, `sub_task`, `pause_after_failure`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (5, 'Test', '0/5 * * * * ?', b'1', 'Task Alarm Test', 'run', NULL, 'Test', 'test', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` (`job_id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`, `description`, `person_in_charge`, `email`, `sub_task`, `pause_after_failure`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (6, 'testTask', '0/5 * * * * ?', b'1', 'Test 3', 'run2', NULL, 'Test 3', 'Zheng Jie', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE `sys_quartz_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) DEFAULT NULL COMMENT 'Bean Name',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'Cron Expression',
  `is_success` bit(1) DEFAULT NULL COMMENT 'Execution Success',
  `job_name` varchar(255) DEFAULT NULL COMMENT 'Job Name',
  `method_name` varchar(255) DEFAULT NULL COMMENT 'Method Name',
  `params` varchar(255) DEFAULT NULL COMMENT 'Parameters',
  `time` bigint(20) DEFAULT NULL COMMENT 'Execution Time',
  `exception_detail` text DEFAULT NULL COMMENT 'Exception Details',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Time',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Scheduled Task Log';

-- ----------------------------
-- Records of sys_quartz_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL COMMENT 'Name',
  `level` int(50) DEFAULT NULL COMMENT 'Role Level',
  `description` varchar(255) DEFAULT NULL COMMENT 'Description',
  `data_scope` varchar(255) DEFAULT NULL COMMENT 'Data Scope',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Date',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`),
  KEY `idx_level` (`level`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Role Table';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`role_id`, `name`, `level`, `description`, `data_scope`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (1, 'Super Administrator', 1, '-', 'All', NULL, 'admin', '2018-11-23 11:04:37', '2020-08-06 16:10:24');
INSERT INTO `sys_role` (`role_id`, `name`, `level`, `description`, `data_scope`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (2, 'Regular User', 2, '-', 'Current Level', NULL, 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
  `role_id` bigint(20) NOT NULL COMMENT 'Role ID',
  `dept_id` bigint(20) NOT NULL COMMENT 'Department ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`),
  KEY `idx_dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Role-Department Association';

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT 'Menu ID',
  `role_id` bigint(20) NOT NULL COMMENT 'Role ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `idx_menu_id` (`menu_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Role-Menu Association';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (1, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (1, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (2, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (2, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (3, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (5, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (6, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (6, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (7, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (7, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (9, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (9, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (10, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (10, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (11, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (11, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (14, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (14, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (15, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (15, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (18, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (19, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (19, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (21, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (21, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (22, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (22, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (23, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (23, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (24, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (24, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (27, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (27, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (28, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (30, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (30, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (32, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (32, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (33, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (33, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (34, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (34, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (35, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (36, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (36, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (37, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (39, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (41, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (44, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (45, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (46, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (48, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (49, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (50, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (52, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (53, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (54, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (56, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (57, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (58, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (60, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (61, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (62, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (64, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (65, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (66, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (73, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (74, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (75, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (77, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (78, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (79, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (80, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (80, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (82, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (82, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (83, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (83, 2);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (90, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (92, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (93, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (94, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (97, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (98, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (102, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (103, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (104, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (105, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (106, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (107, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (108, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (109, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (110, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (111, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (116, 1);
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES (116, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT 'Department Name',
  `username` varchar(180) DEFAULT NULL COMMENT 'Username',
  `nick_name` varchar(255) DEFAULT NULL COMMENT 'Nickname',
  `gender` varchar(2) DEFAULT NULL COMMENT 'Gender',
  `phone` varchar(255) DEFAULT NULL COMMENT 'Phone Number',
  `email` varchar(180) DEFAULT NULL COMMENT 'Email',
  `avatar_name` varchar(255) DEFAULT NULL COMMENT 'Avatar Address',
  `avatar_path` varchar(255) DEFAULT NULL COMMENT 'Avatar Real Path',
  `password` varchar(255) DEFAULT NULL COMMENT 'Password',
  `is_admin` bit(1) DEFAULT b'0' COMMENT 'Is Admin Account',
  `enabled` bit(1) DEFAULT NULL COMMENT 'Status: 1 Enabled, 0 Disabled',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `pwd_reset_time` datetime DEFAULT NULL COMMENT 'Password Reset Time',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Date',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `uniq_email` (`email`) USING BTREE,
  UNIQUE KEY `uniq_username` (`username`) USING BTREE,
  KEY `idx_dept_id` (`dept_id`) USING BTREE,
  KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='System User';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`user_id`, `dept_id`, `username`, `nick_name`, `gender`, `phone`, `email`, `avatar_name`, `avatar_path`, `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`, `update_time`) VALUES (1, 2, 'admin', 'Administrator', 'Male', '18888888888', '201507802@qq.com', 'avatar-20250114101539224.png', '/Users/jie/Documents/work/me/admin/eladmin-mp/eladmin/~/avatar/avatar-20250114101539224.png', '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', b'1', b'1', NULL, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2020-09-05 10:43:31');
INSERT INTO `sys_user` (`user_id`, `dept_id`, `username`, `nick_name`, `gender`, `phone`, `email`, `avatar_name`, `avatar_path`, `password`, `is_admin`, `enabled`, `create_by`, `update_by`, `pwd_reset_time`, `create_time`, `update_time`) VALUES (2, 2, 'test', 'Test', 'Male', '19999999999', '231@qq.com', NULL, NULL, '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', b'0', b'1', 'admin', 'admin', NULL, '2020-05-05 11:15:49', '2020-09-05 10:43:38');
COMMIT;

-- ----------------------------
-- Table structure for sys_users_jobs
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_jobs`;
CREATE TABLE `sys_users_jobs` (
  `user_id` bigint(20) NOT NULL COMMENT 'User ID',
  `job_id` bigint(20) NOT NULL COMMENT 'Job ID',
  PRIMARY KEY (`user_id`,`job_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci COMMENT='User-Job Association Table';

-- ----------------------------
-- Records of sys_users_jobs
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_jobs` (`user_id`, `job_id`) VALUES (1, 11);
INSERT INTO `sys_users_jobs` (`user_id`, `job_id`) VALUES (2, 12);
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
  `user_id` bigint(20) NOT NULL COMMENT 'User ID',
  `role_id` bigint(20) NOT NULL COMMENT 'Role ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='User-Role Association';

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_roles` (`user_id`, `role_id`) VALUES (1, 1);
INSERT INTO `sys_users_roles` (`user_id`, `role_id`) VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for tool_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_alipay_config`;
CREATE TABLE `tool_alipay_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `app_id` varchar(255) DEFAULT NULL COMMENT 'App ID',
  `charset` varchar(255) DEFAULT NULL COMMENT 'Encoding',
  `format` varchar(255) DEFAULT NULL COMMENT 'Type (fixed format: json)',
  `gateway_url` varchar(255) DEFAULT NULL COMMENT 'Gateway URL',
  `notify_url` varchar(255) DEFAULT NULL COMMENT 'Async Callback',
  `private_key` text DEFAULT NULL COMMENT 'Private Key',
  `public_key` text DEFAULT NULL COMMENT 'Public Key',
  `return_url` varchar(255) DEFAULT NULL COMMENT 'Callback URL',
  `sign_type` varchar(255) DEFAULT NULL COMMENT 'Signature Type',
  `sys_service_provider_id` varchar(255) DEFAULT NULL COMMENT 'Merchant ID',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Alipay Configuration';

-- ----------------------------
-- Records of tool_alipay_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `from_user` varchar(255) DEFAULT NULL COMMENT 'Sender',
  `host` varchar(255) DEFAULT NULL COMMENT 'SMTP Server Address',
  `pass` varchar(255) DEFAULT NULL COMMENT 'Password',
  `port` varchar(255) DEFAULT NULL COMMENT 'Port',
  `user` varchar(255) DEFAULT NULL COMMENT 'Sender Username',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Email Configuration';

-- ----------------------------
-- Records of tool_email_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS `tool_local_storage`;
CREATE TABLE `tool_local_storage` (
  `storage_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `real_name` varchar(255) DEFAULT NULL COMMENT 'Real File Name',
  `name` varchar(255) DEFAULT NULL COMMENT 'File Name',
  `suffix` varchar(255) DEFAULT NULL COMMENT 'Suffix',
  `path` varchar(255) DEFAULT NULL COMMENT 'Path',
  `type` varchar(255) DEFAULT NULL COMMENT 'Type',
  `size` varchar(100) DEFAULT NULL COMMENT 'Size',
  `create_by` varchar(255) DEFAULT NULL COMMENT 'Created By',
  `update_by` varchar(255) DEFAULT NULL COMMENT 'Updated By',
  `create_time` datetime DEFAULT NULL COMMENT 'Creation Date',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Local Storage';

-- ----------------------------
-- Records of tool_local_storage
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_config`;
CREATE TABLE `tool_qiniu_config` (
  `config_id` bigint(20) NOT NULL COMMENT 'ID',
  `access_key` text DEFAULT NULL COMMENT 'accessKey',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket Identifier',
  `host` varchar(255) NOT NULL COMMENT 'External Domain',
  `secret_key` text DEFAULT NULL COMMENT 'secretKey',
  `type` varchar(255) DEFAULT NULL COMMENT 'Space Type',
  `zone` varchar(255) DEFAULT NULL COMMENT 'Zone',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Qiniu Cloud Configuration';

-- ----------------------------
-- Records of tool_qiniu_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_content`;
CREATE TABLE `tool_qiniu_content` (
  `content_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) DEFAULT NULL COMMENT 'Bucket Identifier',
  `name` varchar(180) DEFAULT NULL COMMENT 'File Name',
  `size` varchar(255) DEFAULT NULL COMMENT 'File Size',
  `type` varchar(255) DEFAULT NULL COMMENT 'File Type: Private or Public',
  `url` varchar(255) DEFAULT NULL COMMENT 'File URL',
  `suffix` varchar(255) DEFAULT NULL COMMENT 'File Suffix',
  `update_time` datetime DEFAULT NULL COMMENT 'Upload or Sync Time',
  PRIMARY KEY (`content_id`) USING BTREE,
  UNIQUE KEY `uniq_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='Qiniu Cloud File Storage';

-- ----------------------------
-- Records of tool_qiniu_content
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
