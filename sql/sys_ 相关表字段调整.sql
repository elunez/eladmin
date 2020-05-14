select * from sys_user

-- sys_user 表变动 start --
/* id 改为 user_id */
alter table sys_user CHANGE id user_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* 实际操作中需要将 avatar 表的数据移入 sys_user 中 */
alter table sys_user CHANGE avatar_id avatar_url VARCHAR(255) COMMENT '头像地址';
/* 添加头像路径 */
alter table sys_user add avatar_path VARCHAR(255) COMMENT '头像真实路径' AFTER avatar_url;
/* 判断是否为 admin */
alter table sys_user add is_admin bit(1) COMMENT '是否为admin账号' DEFAULT 0 AFTER create_time;
/* sex 改为 gender */
alter table sys_user CHANGE sex gender VARCHAR(2) COMMENT '性别';
/* last_password_reset_time 改为 pwd_reset_time */
alter table sys_user CHANGE last_password_reset_time pwd_reset_time datetime COMMENT '修改密码的时间';
/* 加入通用字段 */
alter table sys_user add update_by VARCHAR(255) COMMENT '更新者' AFTER enabled;
alter table sys_user add create_by VARCHAR(255) COMMENT '创建者' AFTER enabled;
alter table sys_user add update_time datetime COMMENT '更新时间';
-- sys_user end --

-- sys_role 表变动 start-- 
/* id 改为 role_id */
alter table sys_role CHANGE id role_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* remark 改为 description */
alter table sys_role CHANGE remark description VARCHAR(255) COMMENT '描述';
/* 删除权限字段 */
ALTER TABLE sys_role DROP permission;
/* 加入通用字段 */
alter table sys_role add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_role add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_role add update_time datetime COMMENT '更新时间';
-- sys_role end --

-- sys_menu 表变动 start-- 
/* id 改为 menu_id */
alter table sys_menu CHANGE id menu_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* name 改为 title */
alter table sys_menu CHANGE name title VARCHAR(255) COMMENT '菜单标题';
/* component_name 改为 name */
alter table sys_menu CHANGE component_name name VARCHAR(255) COMMENT '组件名称';
/* sort 改为 menu_sort */
alter table sys_menu CHANGE sort menu_sort INT(5) COMMENT '排序';
/* pid 允许为空 */
alter table sys_menu modify pid BIGINT(20) null;
/* 加入子节点数量字段 */
alter table sys_menu add sub_count INT(5) DEFAULT 0 COMMENT '子菜单数目';
/* 加入通用字段 */
alter table sys_menu add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_menu add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_menu add update_time datetime COMMENT '更新时间';
-- sys_menu end --

-- sys_job 表改动 start --
/* id 改为 menu_id */
alter table sys_job CHANGE id job_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* sort 改为 job_sort */
alter table sys_job CHANGE sort job_sort INT(5) COMMENT '排序';
/* 删除岗位与部门的关系 */
ALTER TABLE sys_job DROP dept_id;
/* 加入通用字段 */
alter table sys_job add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_job add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_job add update_time datetime COMMENT '更新时间';
-- sys_job end --

-- sys_dept 表改动 start--
/* id 改为 menu_id */
alter table sys_dept CHANGE id dept_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* pid 允许为空 */
alter table sys_dept modify pid BIGINT(20) null;
/* 加入排序字段 */
alter table sys_dept add dept_sort int(5) DEFAULT 999 COMMENT '排序';
/* 加入子节点数量字段 */
alter table sys_dept add sub_count INT(5) DEFAULT 0 COMMENT '子部门数目';
/* 加入通用字段 */
alter table sys_dept add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_dept add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_dept add update_time datetime COMMENT '更新时间';
-- sys_dept end --

-- sys_dict 表改动 start --
/* id 改为 menu_id */
alter table sys_dict CHANGE id dict_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* remark 改为 description */
alter table sys_dict CHANGE remark description VARCHAR(255) COMMENT '描述';
/* 加入通用字段 */
alter table sys_dict add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_dict add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_dict add update_time datetime COMMENT '更新时间';
-- sys_dict end --

-- sys_dict_detail 表改动 start --
/* id 改为 menu_id */
alter table sys_dict_detail CHANGE id detail_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* sort 改为 dict_sort */
alter table sys_dict_detail CHANGE sort dict_sort INT(5) COMMENT '排序';
/* 加入通用字段 */
alter table sys_dict_detail add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_dict_detail add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_dict_detail add update_time datetime COMMENT '更新时间';
-- sys_dict_detail end --

-- sys_quartz_job and sys_quartz_log 表改动 start --
/* id 改为 job_id */
alter table sys_quartz_job CHANGE id job_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
alter table sys_quartz_log CHANGE id log_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* baen_name 改为 bean_name */
alter table sys_quartz_log CHANGE baen_name bean_name VARCHAR(255) COMMENT 'Bean 名称';
/* remark 改为 description */
alter table sys_quartz_job CHANGE remark description VARCHAR(255) COMMENT '备注';
/* 加入通用字段 */
alter table sys_quartz_job add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_quartz_job add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_quartz_job add update_time datetime COMMENT '更新时间';
/* 添加负责人 与 报警邮箱 */
alter table sys_quartz_job add person_in_charge VARCHAR(100) COMMENT '负责人';
alter table sys_quartz_job add email VARCHAR(100) COMMENT '报警邮箱';
alter table sys_quartz_job add sub_task VARCHAR(100) COMMENT '子任务ID';
alter table sys_quartz_job add pause_after_failure bit(1) COMMENT '任务失败后是否暂停';
-- sys_quartz_job end --

-- sys_monitor_server 表改动 start --
alter table sys_monitor_server CHANGE id monitor_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* sort 改为 monitor_sort */
alter table sys_monitor_server CHANGE sort monitor_sort INT(5) COMMENT '排序';
/* 加入通用字段 */
alter table sys_monitor_server add create_by VARCHAR(255) COMMENT '创建者';
alter table sys_monitor_server add update_by VARCHAR(255) COMMENT '更新者';
alter table sys_monitor_server add create_time datetime COMMENT '创建时间';
alter table sys_monitor_server add update_time datetime COMMENT '更新时间';
-- sys_monitor_server end --

-- sys_log 表改动 start --
alter table sys_log CHANGE id log_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
-- sys_log end --