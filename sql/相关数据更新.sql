/** 将admin改为管理员 */
update sys_user set is_admin = 1 WHERE user_id = 1;
UPDATE sys_dept SET pid = NULL WHERE pid = 0;
UPDATE sys_menu SET pid = NULL WHERE pid = 0;
-- 创建零时表并复制数据 --
create table sys_menu_tmp select * from sys_menu;
create table sys_dept_tmp select * from sys_dept;
-- 更新 sub_count --
UPDATE sys_menu s set s.sub_count = (
	SELECT COUNT(1) FROM sys_menu_tmp tmp WHERE tmp.pid = s.menu_id
);
UPDATE sys_dept d set d.sub_count = (
	SELECT COUNT(1) FROM sys_dept_tmp tmp WHERE tmp.pid = d.dept_id
);
-- 删除零时表 --
DROP TABLE sys_menu_tmp;
DROP TABLE sys_dept_tmp;