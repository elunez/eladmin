
/* 检索并拼接sql语句，复制所有结果删除所有外键 */
SELECT CONCAT('ALTER TABLE ',TABLE_SCHEMA,'.',TABLE_NAME,' DROP FOREIGN KEY ',CONSTRAINT_NAME,' ;') 
FROM information_schema.TABLE_CONSTRAINTS c 
WHERE c.TABLE_SCHEMA='eladmin' AND c.CONSTRAINT_TYPE='FOREIGN KEY';
-- start --
ALTER TABLE eladmin_new.dict_detail DROP FOREIGN KEY dict_detail_ibfk_1 ;
ALTER TABLE eladmin_new.job DROP FOREIGN KEY job_ibfk_1 ;
ALTER TABLE eladmin_new.mnt_deploy DROP FOREIGN KEY mnt_deploy_ibfk_1 ;
ALTER TABLE eladmin_new.mnt_deploy_server DROP FOREIGN KEY mnt_deploy_server_ibfk_1 ;
ALTER TABLE eladmin_new.mnt_deploy_server DROP FOREIGN KEY mnt_deploy_server_ibfk_2 ;
ALTER TABLE eladmin_new.roles_depts DROP FOREIGN KEY roles_depts_ibfk_1 ;
ALTER TABLE eladmin_new.roles_depts DROP FOREIGN KEY roles_depts_ibfk_2 ;
ALTER TABLE eladmin_new.roles_menus DROP FOREIGN KEY roles_menus_ibfk_1 ;
ALTER TABLE eladmin_new.roles_menus DROP FOREIGN KEY roles_menus_ibfk_2 ;
ALTER TABLE eladmin_new.sys_user DROP FOREIGN KEY sys_user_ibfk_1 ;
ALTER TABLE eladmin_new.sys_user DROP FOREIGN KEY sys_user_ibfk_2 ;
ALTER TABLE eladmin_new.sys_user DROP FOREIGN KEY sys_user_ibfk_3 ;
ALTER TABLE eladmin_new.users_roles DROP FOREIGN KEY users_roles_ibfk_1 ;
ALTER TABLE eladmin_new.users_roles DROP FOREIGN KEY users_roles_ibfk_2 ;
-- end --

