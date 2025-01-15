drop table if exists qrtz_fired_triggers;
drop table if exists qrtz_paused_trigger_grps;
drop table if exists qrtz_scheduler_state;
drop table if exists qrtz_locks;
drop table if exists qrtz_simple_triggers;
drop table if exists qrtz_simprop_triggers;
drop table if exists qrtz_cron_triggers;
drop table if exists qrtz_blob_triggers;
drop table if exists qrtz_triggers;
drop table if exists qrtz_job_details;
drop table if exists qrtz_calendars;

create table qrtz_job_details(
sched_name varchar(120) not null,
job_name varchar(200) not null,
job_group varchar(200) not null,
description varchar(250) null,
job_class_name varchar(250) not null,
is_durable varchar(1) not null,
is_nonconcurrent varchar(1) not null,
is_update_data varchar(1) not null,
requests_recovery varchar(1) not null,
job_data blob null,
primary key (sched_name, job_name, job_group))
engine=innodb;

create table qrtz_triggers (
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
job_name varchar(200) not null,
job_group varchar(200) not null,
description varchar(250) null,
next_fire_time bigint(13) null,
prev_fire_time bigint(13) null,
priority integer null,
trigger_state varchar(16) not null,
trigger_type varchar(8) not null,
start_time bigint(13) not null,
end_time bigint(13) null,
calendar_name varchar(200) null,
misfire_instr smallint(2) null,
job_data blob null,
primary key (sched_name, trigger_name, trigger_group),
foreign key (sched_name, job_name, job_group)
references qrtz_job_details(sched_name, job_name, job_group))
engine=innodb;

create table qrtz_simple_triggers (
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
repeat_count bigint(7) not null,
repeat_interval bigint(12) not null,
times_triggered bigint(10) not null,
primary key (sched_name, trigger_name, trigger_group),
foreign key (sched_name, trigger_name, trigger_group)
references qrtz_triggers(sched_name, trigger_name, trigger_group))
engine=innodb;

create table qrtz_cron_triggers (
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
cron_expression varchar(120) not null,
time_zone_id varchar(80),
primary key (sched_name, trigger_name, trigger_group),
foreign key (sched_name, trigger_name, trigger_group)
references qrtz_triggers(sched_name, trigger_name, trigger_group))
engine=innodb;

create table qrtz_simprop_triggers (
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
str_prop_1 varchar(512) null,
str_prop_2 varchar(512) null,
str_prop_3 varchar(512) null,
int_prop_1 int null,
int_prop_2 int null,
long_prop_1 bigint null,
long_prop_2 bigint null,
dec_prop_1 numeric(13, 4) null,
dec_prop_2 numeric(13, 4) null,
bool_prop_1 varchar(1) null,
bool_prop_2 varchar(1) null,
primary key (sched_name, trigger_name, trigger_group),
foreign key (sched_name, trigger_name, trigger_group)
references qrtz_triggers(sched_name, trigger_name, trigger_group))
engine=innodb;

create table qrtz_blob_triggers (
sched_name varchar(120) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
blob_data blob null,
primary key (sched_name, trigger_name, trigger_group),
index (sched_name, trigger_name, trigger_group),
foreign key (sched_name, trigger_name, trigger_group)
references qrtz_triggers(sched_name, trigger_name, trigger_group))
engine=innodb;

create table qrtz_calendars (
sched_name varchar(120) not null,
calendar_name varchar(200) not null,
calendar blob not null,
primary key (sched_name, calendar_name))
engine=innodb;

create table qrtz_paused_trigger_grps (
sched_name varchar(120) not null,
trigger_group varchar(200) not null,
primary key (sched_name, trigger_group))
engine=innodb;

create table qrtz_fired_triggers (
sched_name varchar(120) not null,
entry_id varchar(95) not null,
trigger_name varchar(200) not null,
trigger_group varchar(200) not null,
instance_name varchar(200) not null,
fired_time bigint(13) not null,
sched_time bigint(13) not null,
priority integer not null,
state varchar(16) not null,
job_name varchar(200) null,
job_group varchar(200) null,
is_nonconcurrent varchar(1) null,
requests_recovery varchar(1) null,
primary key (sched_name, entry_id))
engine=innodb;

create table qrtz_scheduler_state (
sched_name varchar(120) not null,
instance_name varchar(200) not null,
last_checkin_time bigint(13) not null,
checkin_interval bigint(13) not null,
primary key (sched_name, instance_name))
engine=innodb;

create table qrtz_locks (
sched_name varchar(120) not null,
lock_name varchar(40) not null,
primary key (sched_name, lock_name))
engine=innodb;

create index idx_qrtz_j_req_recovery on qrtz_job_details(sched_name, requests_recovery);
create index idx_qrtz_j_grp on qrtz_job_details(sched_name, job_group);

create index idx_qrtz_t_j on qrtz_triggers(sched_name, job_name, job_group);
create index idx_qrtz_t_jg on qrtz_triggers(sched_name, job_group);
create index idx_qrtz_t_c on qrtz_triggers(sched_name, calendar_name);
create index idx_qrtz_t_g on qrtz_triggers(sched_name, trigger_group);
create index idx_qrtz_t_state on qrtz_triggers(sched_name, trigger_state);
create index idx_qrtz_t_n_state on qrtz_triggers(sched_name, trigger_name, trigger_group, trigger_state);
create index idx_qrtz_t_n_g_state on qrtz_triggers(sched_name, trigger_group, trigger_state);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(sched_name, next_fire_time);
create index idx_qrtz_t_nft_st on qrtz_triggers(sched_name, trigger_state, next_fire_time);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(sched_name, misfire_instr, next_fire_time);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(sched_name, misfire_instr, next_fire_time, trigger_state);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(sched_name, instance_name);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(sched_name, instance_name, requests_recovery);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(sched_name, job_name, job_group);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(sched_name, job_group);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(sched_name, trigger_name, trigger_group);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(sched_name, trigger_group);

commit;