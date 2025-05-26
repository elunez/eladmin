create table player(
    id          bigint auto_increment primary key,
    name        varchar(32)  not null comment '名称',
    description varchar(255) null comment '描述',
    latitude    double       null comment '纬度',
    longitude   double       null comment '经度',
    profile_image       varchar(255) null comment '图片',
    create_time datetime     null comment '创建时间',
    update_time datetime     null comment '更新时间',
    rate_score  double       null comment '评分',
    user_id     int8         null references sys_user (user_id)
)