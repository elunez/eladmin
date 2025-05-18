create table event_organizer
(
    id         bigint auto_increment primary key,
    user_id    bigint not null references sys_user (user_id),
    club_id    bigint null references club (id)
);

drop table sys_users_jobs;

drop table sys_job;