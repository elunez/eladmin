/**
 将字典详情的 sort 字段改为数值型
 */
alter table dict_detail modify column sort smallint(6);