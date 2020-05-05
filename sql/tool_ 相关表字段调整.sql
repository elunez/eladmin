-- tool_alipay_config 表改动 start --
alter table tool_alipay_config CHANGE id config_id BIGINT(20) COMMENT 'ID';
-- tool_alipay_config end --

-- tool_email_config 表改动 start --
alter table tool_email_config CHANGE id config_id BIGINT(20) COMMENT 'ID';
-- tool_email_config end --

-- tool_local_storage 表改动 start --
alter table tool_local_storage CHANGE id storage_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
/* 加入通用字段 */
alter table tool_local_storage add create_by VARCHAR(255) COMMENT '创建者';
alter table tool_local_storage add update_by VARCHAR(255) COMMENT '更新者';
alter table tool_local_storage add update_time datetime COMMENT '更新时间';
/* 删除 operate 字段 */
ALTER TABLE tool_local_storage DROP operate;
-- tool_local_storage end --

-- tool_picture 表改动 start --
alter table tool_picture CHANGE id picture_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
-- tool_picture end --

-- tool_qiniu_config 表改动 start --
alter table tool_qiniu_config CHANGE id config_id BIGINT(20) COMMENT 'ID';
-- tool_qiniu_config end --

-- tool_qiniu_content 表改动 start --
alter table tool_qiniu_content CHANGE id content_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
-- tool_qiniu_content end --
