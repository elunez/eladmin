# 20220210 代码生成配置表新增列前缀字段
alter table code_gen_config
add column column_prefix varchar(255) null comment '列前缀';
