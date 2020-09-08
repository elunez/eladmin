INSERT INTO eladmin.sys_menu
(menu_id, pid, sub_count, `type`, title, name, component, menu_sort, icon, `path`, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES(NULL, NULL, 1, 1, '${apiAlias}', NULL, '${changeClassName}/index', 999, 'chart', '${changeClassName}', 0, 0, 0, '${changeClassName}:list', 'admin', 'admin', '2020-09-07 11:58:39', '2020-09-08 10:43:43');

INSERT INTO eladmin.sys_menu
(menu_id, pid, sub_count, `type`, title, name, component, menu_sort, icon, `path`, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES(NULL, NULL, 0, 2, '${apiAlias}列表', NULL, NULL, 999, NULL, NULL, 0, 0, 0, '${changeClassName}:list', 'admin', 'admin', '2020-09-08 10:58:29', '2020-09-08 10:58:29');

INSERT INTO eladmin.sys_menu
(menu_id, pid, sub_count, `type`, title, name, component, menu_sort, icon, `path`, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES(NULL, NULL, 0, 2, '${apiAlias}添加', NULL, NULL, 999, NULL, NULL, 0, 0, 0, '${changeClassName}:add', 'admin', 'admin', '2020-09-08 10:58:29', '2020-09-08 10:58:29');

INSERT INTO eladmin.sys_menu
(menu_id, pid, sub_count, `type`, title, name, component, menu_sort, icon, `path`, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES(NULL, NULL, 0, 2, '${apiAlias}修改', NULL, NULL, 999, NULL, NULL, 0, 0, 0, '${changeClassName}:edit', 'admin', 'admin', '2020-09-08 10:58:29', '2020-09-08 10:58:29');

INSERT INTO eladmin.sys_menu
(menu_id, pid, sub_count, `type`, title, name, component, menu_sort, icon, `path`, i_frame, cache, hidden, permission, create_by, update_by, create_time, update_time)
VALUES(NULL, NULL, 0, 2, '${apiAlias}删除', NULL, NULL, 999, NULL, NULL, 0, 0, 0, '${changeClassName}:del', 'admin', 'admin', '2020-09-08 10:58:29', '2020-09-08 10:58:29');