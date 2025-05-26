create table sport
(
    id          bigint auto_increment primary key,
    name        varchar(32)  not null comment '名称',
    description varchar(255) null comment '描述',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间',
    icon        varchar(255) null comment '图标',
    sort        int          null comment '排序',
    enabled     bit          null comment '是否启用'
);

create table club
(
    id          bigint auto_increment primary key,
    name        varchar(32)  not null comment '名称',
    description varchar(255) null comment '描述',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间',
    icon        varchar(255) null comment '图标',
    sort        int          null comment '排序',
    enabled     bit          null comment '是否启用',
    location    varchar(255) null comment '位置',
    longitude   double       null comment '经度',
    latitude    double       null comment '纬度'
);

create table court
(
    id          bigint auto_increment primary key,
    club_id     bigint   null references club (id),
    sport_id    bigint   null references sport (id),
    create_time datetime null comment '创建时间',
    update_time datetime null comment '更新时间',
    amount      int      not null default 0 comment '数量'
);

create table event
(
    id          bigint auto_increment primary key,
    name        varchar(32)  not null comment '名称',
    description varchar(255) null comment '描述',
    format      enum ('SINGLE', 'DOUBLE', 'TEAM') not null,
    location    varchar(255) null comment '位置',
    image       varchar(255) null comment '图片',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间',
    sort        int          null comment '排序',
    enabled     bit          null comment '是否启用',
    event_time  datetime     null comment '时间',
    club_id     bigint       null references club (id),
    create_by   bigint       null references sys_user (user_id)
);
