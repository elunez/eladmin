/** 系统相关表统一 sys 前缀 */
ALTER TABLE `user` RENAME TO `sys_user`;
ALTER TABLE `role` RENAME TO `sys_role`;
ALTER TABLE `menu` RENAME TO `sys_menu`;
ALTER TABLE `dept` RENAME TO `sys_dept`;
ALTER TABLE `job` RENAME TO `sys_job`;
ALTER TABLE `dict` RENAME TO `sys_dict`;
ALTER TABLE `dict_detail` RENAME TO `sys_dict_detail`;
ALTER TABLE `log` RENAME TO `sys_log`;
ALTER TABLE `visits` RENAME TO `sys_visits`;
ALTER TABLE `verification_code` RENAME TO `sys_verification_code`;
ALTER TABLE `monitor_server` RENAME TO `sys_monitor_server`;
ALTER TABLE `quartz_job` RENAME TO `sys_quartz_job`;
ALTER TABLE `quartz_log` RENAME TO `sys_quartz_log`;
ALTER TABLE `users_roles` RENAME TO `sys_users_roles`;
ALTER TABLE `roles_menus` RENAME TO `sys_roles_menus`;
ALTER TABLE `roles_depts` RENAME TO `sys_roles_depts`;

/** 工具相关表 */
ALTER TABLE `alipay_config` RENAME TO `tool_alipay_config`;
ALTER TABLE `email_config` RENAME TO `tool_email_config`;
ALTER TABLE `local_storage` RENAME TO `tool_local_storage`;
ALTER TABLE `picture` RENAME TO `tool_picture`;
ALTER TABLE `qiniu_config` RENAME TO `tool_qiniu_config`;
ALTER TABLE `qiniu_content` RENAME TO `tool_qiniu_content`;

/** 代码生成器相关 */
ALTER TABLE `column_config` RENAME TO `code_column_config`;
ALTER TABLE `gen_config` RENAME TO `code_gen_config`;


