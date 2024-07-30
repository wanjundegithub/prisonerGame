create database `prisoner`;

create table `prisoner`.`permission`
(
    `id` int(11) not null primary key auto_increment comment '权限表id',
    `role` varchar(30) not null comment '权限表角色，仅有管理员角色admin和normal',
    `school_number` varchar(30) not null comment '学号',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0 代表删除 1代表未删除'
)engine = InnoDB, charset = utf8mb4;

create table `prisoner`.`user`
(
    `id` int(11) not null primary key auto_increment comment '用户表id',
    `nick_name` varchar(30) not null comment '昵称',
    `user_name` varchar(30) not null comment '用户名-学号',
    `password` varchar(30) not null comment '初始密码-学号',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0 代表删除 1代表未删除'
)engine = InnoDB, charset = utf8mb4;