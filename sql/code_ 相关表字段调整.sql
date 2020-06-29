-- code_gen_config 表改动 start --
alter table code_gen_config CHANGE id config_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
-- code_gen_config end --

-- code_column_config 表改动 start --
alter table code_column_config CHANGE id column_id BIGINT(20) AUTO_INCREMENT COMMENT 'ID';
-- code_column_config end --