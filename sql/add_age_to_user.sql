-- Add age column to sys_user table
ALTER TABLE sys_user ADD COLUMN age INT DEFAULT NULL COMMENT '年龄';
