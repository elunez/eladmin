-- code_ --
CREATE INDEX idx_table_name ON code_column_config(table_name);

CREATE INDEX idx_table_name ON code_gen_config(table_name);

-- mnt_ --
CREATE INDEX idx_ip ON mnt_server(ip);

-- sys_ --
CREATE INDEX inx_is_pause ON sys_quartz_job(is_pause);

CREATE INDEX inx_pid ON sys_dept(pid);
CREATE INDEX inx_enabled ON sys_dept(enabled);

CREATE UNIQUE INDEX uniq_name ON sys_job(name);
CREATE INDEX inx_enabled ON sys_job(enabled);

CREATE UNIQUE INDEX uniq_title ON sys_menu(title);
-- 将 ‘’ 设置成 NULL --
UPDATE sys_menu set name = NULL WHERE name = '';
CREATE UNIQUE INDEX uniq_name ON sys_menu(name);
CREATE INDEX inx_pid ON sys_menu(pid);

CREATE UNIQUE INDEX uniq_name ON sys_role(name);

CREATE UNIQUE INDEX uniq_username ON sys_user(username);
CREATE UNIQUE INDEX uniq_email ON sys_user(email);
CREATE INDEX inx_enabled ON sys_user(enabled);

CREATE INDEX inx_log_type ON sys_log(log_type);

-- tool_ --
-- 将 ‘’ 设置成 NULL --
UPDATE tool_picture set md5code = NULL WHERE md5code = '';
CREATE UNIQUE INDEX uniq_md5_code ON tool_picture(md5code);
CREATE INDEX inx_url ON tool_picture(url);

CREATE UNIQUE INDEX uniq_name ON tool_qiniu_content(name);