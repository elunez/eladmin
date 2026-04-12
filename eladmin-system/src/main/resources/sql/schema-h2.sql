DROP TABLE IF EXISTS code_column;
CREATE TABLE code_column (
  column_id BIGINT NOT NULL AUTO_INCREMENT,
  table_name VARCHAR(180),
  column_name VARCHAR(255),
  column_type VARCHAR(255),
  dict_name VARCHAR(255),
  extra VARCHAR(255),
  form_show BOOLEAN,
  form_type VARCHAR(255),
  key_type VARCHAR(255),
  list_show BOOLEAN,
  not_null BOOLEAN,
  query_type VARCHAR(255),
  remark VARCHAR(255),
  date_annotation VARCHAR(255),
  PRIMARY KEY (column_id)
);
CREATE INDEX idx_code_column_table_name ON code_column (table_name);

DROP TABLE IF EXISTS code_config;
CREATE TABLE code_config (
  config_id BIGINT NOT NULL AUTO_INCREMENT,
  table_name VARCHAR(255),
  author VARCHAR(255),
  cover BOOLEAN,
  module_name VARCHAR(255),
  pack VARCHAR(255),
  path VARCHAR(255),
  api_path VARCHAR(255),
  prefix VARCHAR(255),
  api_alias VARCHAR(255),
  PRIMARY KEY (config_id)
);
CREATE INDEX idx_code_config_table_name ON code_config (table_name);

DROP TABLE IF EXISTS mnt_app;
CREATE TABLE mnt_app (
  app_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  upload_path VARCHAR(255),
  deploy_path VARCHAR(255),
  backup_path VARCHAR(255),
  port INT,
  start_script VARCHAR(4000),
  deploy_script VARCHAR(4000),
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (app_id)
);

DROP TABLE IF EXISTS mnt_database;
CREATE TABLE mnt_database (
  db_id VARCHAR(50) NOT NULL,
  name VARCHAR(255) NOT NULL,
  jdbc_url VARCHAR(255) NOT NULL,
  user_name VARCHAR(255) NOT NULL,
  pwd VARCHAR(255) NOT NULL,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (db_id)
);

DROP TABLE IF EXISTS mnt_deploy;
CREATE TABLE mnt_deploy (
  deploy_id BIGINT NOT NULL AUTO_INCREMENT,
  app_id BIGINT,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (deploy_id)
);
CREATE INDEX idx_mnt_deploy_app_id ON mnt_deploy (app_id);

DROP TABLE IF EXISTS mnt_deploy_history;
CREATE TABLE mnt_deploy_history (
  history_id VARCHAR(50) NOT NULL,
  app_name VARCHAR(255) NOT NULL,
  deploy_date TIMESTAMP NOT NULL,
  deploy_user VARCHAR(50) NOT NULL,
  ip VARCHAR(20) NOT NULL,
  deploy_id BIGINT,
  PRIMARY KEY (history_id)
);

DROP TABLE IF EXISTS mnt_deploy_server;
CREATE TABLE mnt_deploy_server (
  deploy_id BIGINT NOT NULL,
  server_id BIGINT NOT NULL,
  PRIMARY KEY (deploy_id, server_id)
);
CREATE INDEX idx_mnt_ds_deploy_id ON mnt_deploy_server (deploy_id);
CREATE INDEX idx_mnt_ds_server_id ON mnt_deploy_server (server_id);

DROP TABLE IF EXISTS mnt_server;
CREATE TABLE mnt_server (
  server_id BIGINT NOT NULL AUTO_INCREMENT,
  account VARCHAR(50),
  ip VARCHAR(20),
  name VARCHAR(100),
  password VARCHAR(100),
  port INT,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (server_id)
);
CREATE INDEX idx_mnt_server_ip ON mnt_server (ip);

DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
  dept_id BIGINT NOT NULL AUTO_INCREMENT,
  pid BIGINT,
  sub_count INT DEFAULT 0,
  name VARCHAR(255) NOT NULL,
  dept_sort INT DEFAULT 999,
  enabled BOOLEAN NOT NULL,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (dept_id)
);
CREATE INDEX idx_sys_dept_pid ON sys_dept (pid);
CREATE INDEX idx_sys_dept_enabled ON sys_dept (enabled);

DROP TABLE IF EXISTS sys_dict;
CREATE TABLE sys_dict (
  dict_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (dict_id)
);

DROP TABLE IF EXISTS sys_dict_detail;
CREATE TABLE sys_dict_detail (
  detail_id BIGINT NOT NULL AUTO_INCREMENT,
  dict_id BIGINT,
  label VARCHAR(255) NOT NULL,
  value VARCHAR(255) NOT NULL,
  dict_sort INT,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (detail_id)
);
CREATE INDEX idx_sys_dict_detail_dict_id ON sys_dict_detail (dict_id);

DROP TABLE IF EXISTS sys_job;
CREATE TABLE sys_job (
  job_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(180) NOT NULL,
  enabled BOOLEAN NOT NULL,
  job_sort INT,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (job_id),
  UNIQUE (name)
);
CREATE INDEX idx_sys_job_enabled ON sys_job (enabled);

DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log (
  log_id BIGINT NOT NULL AUTO_INCREMENT,
  description VARCHAR(255),
  log_type VARCHAR(10) NOT NULL,
  method VARCHAR(255),
  params CLOB,
  request_ip VARCHAR(255),
  time BIGINT,
  username VARCHAR(255),
  address VARCHAR(255),
  browser VARCHAR(255),
  exception_detail CLOB,
  create_time TIMESTAMP NOT NULL,
  PRIMARY KEY (log_id)
);
CREATE INDEX idx_sys_log_create_time ON sys_log (create_time);
CREATE INDEX idx_sys_log_log_type ON sys_log (log_type);

DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
  menu_id BIGINT NOT NULL AUTO_INCREMENT,
  pid BIGINT,
  sub_count INT DEFAULT 0,
  type INT,
  title VARCHAR(100),
  name VARCHAR(100),
  component VARCHAR(255),
  menu_sort INT,
  icon VARCHAR(255),
  path VARCHAR(255),
  i_frame BOOLEAN,
  cache BOOLEAN DEFAULT FALSE,
  hidden BOOLEAN DEFAULT FALSE,
  permission VARCHAR(255),
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (menu_id),
  UNIQUE (name)
);
CREATE INDEX idx_sys_menu_pid ON sys_menu (pid);

DROP TABLE IF EXISTS sys_quartz_job;
CREATE TABLE sys_quartz_job (
  job_id BIGINT NOT NULL AUTO_INCREMENT,
  bean_name VARCHAR(255),
  cron_expression VARCHAR(255),
  is_pause BOOLEAN,
  job_name VARCHAR(255),
  method_name VARCHAR(255),
  params VARCHAR(255),
  description VARCHAR(255),
  person_in_charge VARCHAR(100),
  email VARCHAR(100),
  sub_task VARCHAR(100),
  pause_after_failure BOOLEAN,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (job_id)
);
CREATE INDEX idx_sys_quartz_job_is_pause ON sys_quartz_job (is_pause);

DROP TABLE IF EXISTS sys_quartz_log;
CREATE TABLE sys_quartz_log (
  log_id BIGINT NOT NULL AUTO_INCREMENT,
  bean_name VARCHAR(255),
  cron_expression VARCHAR(255),
  is_success BOOLEAN,
  job_name VARCHAR(255),
  method_name VARCHAR(255),
  params VARCHAR(255),
  time BIGINT,
  exception_detail CLOB,
  create_time TIMESTAMP,
  PRIMARY KEY (log_id)
);

DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
  role_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  level INT,
  description VARCHAR(255),
  data_scope VARCHAR(255),
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (role_id),
  UNIQUE (name)
);
CREATE INDEX idx_sys_role_level ON sys_role (level);

DROP TABLE IF EXISTS sys_roles_depts;
CREATE TABLE sys_roles_depts (
  role_id BIGINT NOT NULL,
  dept_id BIGINT NOT NULL,
  PRIMARY KEY (role_id, dept_id)
);
CREATE INDEX idx_sys_roles_depts_role_id ON sys_roles_depts (role_id);
CREATE INDEX idx_sys_roles_depts_dept_id ON sys_roles_depts (dept_id);

DROP TABLE IF EXISTS sys_roles_menus;
CREATE TABLE sys_roles_menus (
  menu_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (menu_id, role_id)
);
CREATE INDEX idx_sys_roles_menus_menu_id ON sys_roles_menus (menu_id);
CREATE INDEX idx_sys_roles_menus_role_id ON sys_roles_menus (role_id);

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  dept_id BIGINT,
  username VARCHAR(180),
  nick_name VARCHAR(255),
  gender VARCHAR(2),
  phone VARCHAR(255),
  email VARCHAR(180),
  avatar_name VARCHAR(255),
  avatar_path VARCHAR(255),
  password VARCHAR(255),
  is_admin BOOLEAN DEFAULT FALSE,
  enabled BOOLEAN,
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  pwd_reset_time TIMESTAMP,
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (user_id),
  UNIQUE (email),
  UNIQUE (username)
);
CREATE INDEX idx_sys_user_dept_id ON sys_user (dept_id);
CREATE INDEX idx_sys_user_enabled ON sys_user (enabled);

DROP TABLE IF EXISTS sys_users_jobs;
CREATE TABLE sys_users_jobs (
  user_id BIGINT NOT NULL,
  job_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, job_id)
);
CREATE INDEX idx_sys_users_jobs_user_id ON sys_users_jobs (user_id);
CREATE INDEX idx_sys_users_jobs_job_id ON sys_users_jobs (job_id);

DROP TABLE IF EXISTS sys_users_roles;
CREATE TABLE sys_users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id)
);
CREATE INDEX idx_sys_users_roles_user_id ON sys_users_roles (user_id);
CREATE INDEX idx_sys_users_roles_role_id ON sys_users_roles (role_id);

DROP TABLE IF EXISTS tool_alipay_config;
CREATE TABLE tool_alipay_config (
  config_id BIGINT NOT NULL,
  app_id VARCHAR(255),
  charset VARCHAR(255),
  format VARCHAR(255),
  gateway_url VARCHAR(255),
  notify_url VARCHAR(255),
  private_key CLOB,
  public_key CLOB,
  return_url VARCHAR(255),
  sign_type VARCHAR(255),
  sys_service_provider_id VARCHAR(255),
  PRIMARY KEY (config_id)
);

DROP TABLE IF EXISTS tool_email_config;
CREATE TABLE tool_email_config (
  config_id BIGINT NOT NULL,
  from_user VARCHAR(255),
  host VARCHAR(255),
  pass VARCHAR(255),
  port VARCHAR(255),
  user VARCHAR(255),
  PRIMARY KEY (config_id)
);

DROP TABLE IF EXISTS tool_local_storage;
CREATE TABLE tool_local_storage (
  storage_id BIGINT NOT NULL AUTO_INCREMENT,
  real_name VARCHAR(255),
  name VARCHAR(255),
  suffix VARCHAR(255),
  path VARCHAR(255),
  type VARCHAR(255),
  size VARCHAR(100),
  create_by VARCHAR(255),
  update_by VARCHAR(255),
  create_time TIMESTAMP,
  update_time TIMESTAMP,
  PRIMARY KEY (storage_id)
);

DROP TABLE IF EXISTS tool_s3_storage;
CREATE TABLE tool_s3_storage (
  storage_id BIGINT NOT NULL AUTO_INCREMENT,
  file_name VARCHAR(255) NOT NULL,
  file_real_name VARCHAR(255) NOT NULL,
  file_size VARCHAR(100) NOT NULL,
  file_mime_type VARCHAR(50) NOT NULL,
  file_type VARCHAR(50) NOT NULL,
  file_path TEXT NOT NULL,
  create_by VARCHAR(255) NOT NULL,
  update_by VARCHAR(255) NOT NULL,
  create_time TIMESTAMP NOT NULL,
  update_time TIMESTAMP NOT NULL,
  PRIMARY KEY (storage_id)
);

DROP TABLE IF EXISTS qrtz_fired_triggers;
DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
DROP TABLE IF EXISTS qrtz_scheduler_state;
DROP TABLE IF EXISTS qrtz_locks;
DROP TABLE IF EXISTS qrtz_simple_triggers;
DROP TABLE IF EXISTS qrtz_simprop_triggers;
DROP TABLE IF EXISTS qrtz_cron_triggers;
DROP TABLE IF EXISTS qrtz_blob_triggers;
DROP TABLE IF EXISTS qrtz_triggers;
DROP TABLE IF EXISTS qrtz_job_details;
DROP TABLE IF EXISTS qrtz_calendars;

CREATE TABLE qrtz_job_details (
  sched_name VARCHAR(120) NOT NULL,
  job_name VARCHAR(200) NOT NULL,
  job_group VARCHAR(200) NOT NULL,
  description VARCHAR(250),
  job_class_name VARCHAR(250) NOT NULL,
  is_durable VARCHAR(1) NOT NULL,
  is_nonconcurrent VARCHAR(1) NOT NULL,
  is_update_data VARCHAR(1) NOT NULL,
  requests_recovery VARCHAR(1) NOT NULL,
  job_data BLOB,
  PRIMARY KEY (sched_name, job_name, job_group)
);

CREATE TABLE qrtz_triggers (
  sched_name VARCHAR(120) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  job_name VARCHAR(200) NOT NULL,
  job_group VARCHAR(200) NOT NULL,
  description VARCHAR(250),
  next_fire_time BIGINT,
  prev_fire_time BIGINT,
  priority INT,
  trigger_state VARCHAR(16) NOT NULL,
  trigger_type VARCHAR(8) NOT NULL,
  start_time BIGINT NOT NULL,
  end_time BIGINT,
  calendar_name VARCHAR(200),
  misfire_instr SMALLINT,
  job_data BLOB,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT qrtz_triggers_fk FOREIGN KEY (sched_name, job_name, job_group)
    REFERENCES qrtz_job_details (sched_name, job_name, job_group)
);

CREATE TABLE qrtz_simple_triggers (
  sched_name VARCHAR(120) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  repeat_count BIGINT NOT NULL,
  repeat_interval BIGINT NOT NULL,
  times_triggered BIGINT NOT NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT qrtz_simple_triggers_fk FOREIGN KEY (sched_name, trigger_name, trigger_group)
    REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_cron_triggers (
  sched_name VARCHAR(120) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  cron_expression VARCHAR(120) NOT NULL,
  time_zone_id VARCHAR(80),
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT qrtz_cron_triggers_fk FOREIGN KEY (sched_name, trigger_name, trigger_group)
    REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_simprop_triggers (
  sched_name VARCHAR(120) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  str_prop_1 VARCHAR(512),
  str_prop_2 VARCHAR(512),
  str_prop_3 VARCHAR(512),
  int_prop_1 INT,
  int_prop_2 INT,
  long_prop_1 BIGINT,
  long_prop_2 BIGINT,
  dec_prop_1 DECIMAL(13, 4),
  dec_prop_2 DECIMAL(13, 4),
  bool_prop_1 VARCHAR(1),
  bool_prop_2 VARCHAR(1),
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT qrtz_simprop_triggers_fk FOREIGN KEY (sched_name, trigger_name, trigger_group)
    REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_blob_triggers (
  sched_name VARCHAR(120) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  blob_data BLOB,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT qrtz_blob_triggers_fk FOREIGN KEY (sched_name, trigger_name, trigger_group)
    REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group)
);

CREATE TABLE qrtz_calendars (
  sched_name VARCHAR(120) NOT NULL,
  calendar_name VARCHAR(200) NOT NULL,
  calendar BLOB NOT NULL,
  PRIMARY KEY (sched_name, calendar_name)
);

CREATE TABLE qrtz_paused_trigger_grps (
  sched_name VARCHAR(120) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  PRIMARY KEY (sched_name, trigger_group)
);

CREATE TABLE qrtz_fired_triggers (
  sched_name VARCHAR(120) NOT NULL,
  entry_id VARCHAR(95) NOT NULL,
  trigger_name VARCHAR(200) NOT NULL,
  trigger_group VARCHAR(200) NOT NULL,
  instance_name VARCHAR(200) NOT NULL,
  fired_time BIGINT NOT NULL,
  sched_time BIGINT NOT NULL,
  priority INT NOT NULL,
  state VARCHAR(16) NOT NULL,
  job_name VARCHAR(200),
  job_group VARCHAR(200),
  is_nonconcurrent VARCHAR(1),
  requests_recovery VARCHAR(1),
  PRIMARY KEY (sched_name, entry_id)
);

CREATE TABLE qrtz_scheduler_state (
  sched_name VARCHAR(120) NOT NULL,
  instance_name VARCHAR(200) NOT NULL,
  last_checkin_time BIGINT NOT NULL,
  checkin_interval BIGINT NOT NULL,
  PRIMARY KEY (sched_name, instance_name)
);

CREATE TABLE qrtz_locks (
  sched_name VARCHAR(120) NOT NULL,
  lock_name VARCHAR(40) NOT NULL,
  PRIMARY KEY (sched_name, lock_name)
);

CREATE INDEX idx_qrtz_j_req_recovery ON qrtz_job_details (sched_name, requests_recovery);
CREATE INDEX idx_qrtz_j_grp ON qrtz_job_details (sched_name, job_group);
CREATE INDEX idx_qrtz_t_j ON qrtz_triggers (sched_name, job_name, job_group);
CREATE INDEX idx_qrtz_t_jg ON qrtz_triggers (sched_name, job_group);
CREATE INDEX idx_qrtz_t_c ON qrtz_triggers (sched_name, calendar_name);
CREATE INDEX idx_qrtz_t_g ON qrtz_triggers (sched_name, trigger_group);
CREATE INDEX idx_qrtz_t_state ON qrtz_triggers (sched_name, trigger_state);
CREATE INDEX idx_qrtz_t_n_state ON qrtz_triggers (sched_name, trigger_name, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_t_n_g_state ON qrtz_triggers (sched_name, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_t_next_fire_time ON qrtz_triggers (sched_name, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_st ON qrtz_triggers (sched_name, trigger_state, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_misfire ON qrtz_triggers (sched_name, misfire_instr, next_fire_time);
CREATE INDEX idx_qrtz_t_nft_st_misfire ON qrtz_triggers (sched_name, misfire_instr, next_fire_time, trigger_state);
CREATE INDEX idx_qrtz_t_nft_st_misfire_grp ON qrtz_triggers (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);
CREATE INDEX idx_qrtz_ft_trig_inst_name ON qrtz_fired_triggers (sched_name, instance_name);
CREATE INDEX idx_qrtz_ft_inst_job_req_rcvry ON qrtz_fired_triggers (sched_name, instance_name, requests_recovery);
CREATE INDEX idx_qrtz_ft_j_g ON qrtz_fired_triggers (sched_name, job_name, job_group);
CREATE INDEX idx_qrtz_ft_jg ON qrtz_fired_triggers (sched_name, job_group);
CREATE INDEX idx_qrtz_ft_t_g ON qrtz_fired_triggers (sched_name, trigger_name, trigger_group);
CREATE INDEX idx_qrtz_ft_tg ON qrtz_fired_triggers (sched_name, trigger_group);
