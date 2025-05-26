alter table event
    add column status varchar(255),
    add column is_public bit default 0,
    add column allow_wait_list bit default 0;
