/** 将admin改为管理员 */
update sys_user set is_admin = 1 WHERE user_id = 1
UPDATE sys_dept SET pid = NULL WHERE pid = 0;
UPDATE sys_menu SET pid = NULL WHERE pid = 0;