alter table event
    add column public_link varchar(255);

create table match_group
(
    id          bigint auto_increment primary key,
    create_time datetime,
    size        int default 2 not null,
    name        varchar(255)  not null,
    score       int default 0
);

create table group_player
(
    id        bigint auto_increment primary key,
    player_id bigint not null references player (id),
    group_id  bigint not null references match_group (id)
);

create table sport_match
(
    id          bigint auto_increment primary key,
    event_id    bigint       not null references event (id),
    name        varchar(32)  not null comment '名称',
    create_time datetime     null comment '创建时间',
    enabled     bit          null comment '是否启用'
);