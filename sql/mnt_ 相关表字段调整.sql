-- mnt_app 表改动 start --
alter table mnt_app CHANGE id app_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* 加入通用字段 */
alter table mnt_app add create_by VARCHAR(255) COMMENT '创建者';
alter table mnt_app add update_by VARCHAR(255) COMMENT '更新者';
alter table mnt_app add update_time datetime COMMENT '更新时间';
-- mnt_app end --

-- mnt_database 表改动 start --
alter table mnt_database CHANGE id db_id VARCHAR(50) COMMENT 'ID';
/* 加入通用字段 */
alter table mnt_database add create_by VARCHAR(255) COMMENT '创建者';
alter table mnt_database add update_by VARCHAR(255) COMMENT '更新者';
alter table mnt_database add update_time datetime COMMENT '更新时间';
-- mnt_database end --

-- mnt_deploy 表改动 start --
alter table mnt_deploy CHANGE id deploy_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* 加入通用字段 */
alter table mnt_deploy add create_by VARCHAR(255) COMMENT '创建者';
alter table mnt_deploy add update_by VARCHAR(255) COMMENT '更新者';
alter table mnt_deploy add update_time datetime COMMENT '更新时间';
-- mnt_deploy end --

-- mnt_server 表改动 start --
alter table mnt_server CHANGE id server_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* 加入通用字段 */
alter table mnt_server add create_by VARCHAR(255) COMMENT '创建者';
alter table mnt_server add update_by VARCHAR(255) COMMENT '更新者';
alter table mnt_server add update_time datetime COMMENT '更新时间';
-- mnt_server end --

-- mnt_deploy_history 表改动 start --
alter table mnt_deploy_history CHANGE id history_id VARCHAR(50) COMMENT 'ID';
-- mnt_deploy end --
